package com.example.middemo;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MapFragment extends Fragment {
    private Context context;

    Handler handler = new Handler(Looper.getMainLooper());

    EditText search_box; //주소 검색창
    String lat, lon;

    /* TMap 세팅*/
    TMapData tMapData = new TMapData();
    TMapView tMapView;
    TMapMarkerItem markerItem = new TMapMarkerItem();
    Button register_btn;

    UserLocation registerLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map, container,false);
        context = container.getContext();
        registerLocation = new UserLocation(0,0);

        /* 지도 세팅 */
        RelativeLayout relativeLayoutTmap = (RelativeLayout) rootView.findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(getActivity());
        tMapView.setSKTMapApiKey("887bc5c0-3f62-4cc7-b1b9-957f10d3dfc6");
        tMapView.setHttpsMode(true);
        relativeLayoutTmap.addView( tMapView );
        tMapView.setCenterPoint( 126.985302, 37.570841 );

        markerItem.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        tMapView.setCenterPoint( 126.985302, 37.570841 );

        /* 검색 로직 */
        search_box = (EditText) rootView.findViewById(R.id.editText);
        search_box.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){

                    if ( search_box.getText().toString().length() == 0 ) { // 검색창에 빈 스트링 입력 했을 경우
                        Toast.makeText(context, "검색어를 입력 해 주세요", Toast.LENGTH_LONG).show();

                    } else {
                        // 검색 결과 반환 callBack 메소드
                        tMapData.findAllPOI(search_box.getText().toString(), new TMapData.FindAllPOIListenerCallback() {
                            @Override
                            public void onFindAllPOI(ArrayList poiItem) {
                                for(int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = (TMapPOIItem) poiItem.get(i);
                                    Log.d("POI Name: ", item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "")  + ", " +
                                            "Point: " + item.getPOIPoint().toString());
                                }
                                if(poiItem.size()==0){
                                    Toast.makeText(context, "검색 결과가 없습니다.", Toast.LENGTH_LONG).show();
                                } else {
                                    TMapPOIItem item = (TMapPOIItem) poiItem.get(0);
                                    markerItem.setTMapPoint( item.getPOIPoint() ); // 마커의 좌표 지정
                                    tMapView.addMarkerItem("markerItem1", markerItem); // 지도에 마커 추가
                                    lat = item.getPOIPoint().toString().split(" ")[1];
                                    lon = item.getPOIPoint().toString().split(" ")[3];
                                    tMapView.setCenterPoint( Double.parseDouble(lon), Double.parseDouble(lat) ); // 지도 이동
                                    registerLocation.setLongitude(Double.parseDouble(lon));
                                    registerLocation.setLatitude(Double.parseDouble(lat));
                                }
                                Log.d("finish Tag", "검색 끝");
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });

        /* MAP 터치 이벤트*/
        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            double pressed_X;
            double pressed_Y;
            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                Log.d("x" , Float.toString(pointF.x));
                Log.d("y" , Float.toString(pointF.y));
                Log.d("x", tMapPoint.toString());
                lat = tMapPoint.toString().split(" ")[1];
                lon = tMapPoint.toString().split(" ")[3];
                pressed_X = Double.parseDouble(lon);
                pressed_Y = Double.parseDouble(lat);
                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                Log.d("x" , Float.toString(pointF.x));
                Log.d("y" , Float.toString(pointF.y));
                Log.d("x", tMapPoint.toString());
                lat = tMapPoint.toString().split(" ")[1];
                lon = tMapPoint.toString().split(" ")[3];
                String result = "위도 : " + lat + "경도 : " + lon;
                if(Double.parseDouble(lon) == pressed_X && Double.parseDouble(lat) == pressed_Y){
                    Log.d("마커!","마커!");
                    markerItem.setTMapPoint( tMapPoint ); // 마커의 좌표 지정
                    registerLocation.setLongitude(Double.parseDouble(lon));
                    registerLocation.setLatitude(Double.parseDouble(lat));
                }
                return false;
            }
        });

        register_btn = (Button) rootView.findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).registerLocation(registerLocation);
                ((MainActivity)getActivity()).hideFragment();
            }
        });
        return rootView;
    }
}
