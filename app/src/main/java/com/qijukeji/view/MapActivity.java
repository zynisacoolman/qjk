package com.qijukeji.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.qijukeji.adapter.MapListAdapter;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;


public class MapActivity extends AppCompatActivity {
    private static final String TAG = MapActivity.class.getSimpleName();
    @Bind(R.id.ll_search)
    LinearLayout ll_search;
    @Bind(R.id.lv_map_name)
    ListView lvMapName;
    MapListAdapter mapAdapyer;
    @Bind(R.id.tv_housing_estate)
    TextView tvHousingEstate;
    @Bind(R.id.tv_map_all)
    TextView tvMapAll;
    private MapView mapView;
    private AMap aMap;
    private Context context;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    private List<PoiItem> poiItems;
    Marker screenMarker = null;
    public static final int INTENT_RETURN = 10;
    private static final int INTENT_KEY = 11;
    private LatLng latlng = new LatLng(39.761, 116.434);
    private LatLng latLng;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = new Date(amapLocation.getTime());
//                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 19));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title("当前位置");
                    markerOptions.visible(true);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker));
                    markerOptions.icon(bitmapDescriptor);
                    aMap.addMarker(markerOptions);
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                }
            }
        }
    };

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double lat;
    private double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        context = this;
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);//必须要写

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
//        Intent intent = getIntent();
//        controller = (IntentionDetailsController) intent.getCharSequenceExtra("context");
        init();

    }

    /**
     * *初始化AMap对象
     */

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        setUpMap();

    }

    private void setUpMap() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        UiSettings mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(true);
        //比例尺
        mUiSettings.setScaleControlsEnabled(true);
        //指南针
        mUiSettings.setCompassEnabled(true);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter();
            }
        });

        // 设置可视范围变化时的回调的接口方法
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition postion) {
                //屏幕中心的Marker跳动
                startJumpAnimation();
            }
        });
    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {

        if (screenMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            latLng = screenMarker.getPosition();
            doSearchQuery(latLng);
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= dip2px(this, 125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            screenMarker.setAnimation(animation);
            //开始动画
            screenMarker.startAnimation();

        } else {
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(LatLng latLng) {
        int currentPage = 0;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        //PoiSearch.Query query = new PoiSearch.Query("店铺|餐饮|服务|购物|生活|住宿|汽车|休闲|保健|旅游", "", "深圳");
        //第一、二参数为空串好像是搜索所有能搜到的内容
        PoiSearch.Query query = new PoiSearch.Query("住宅区", "", "石家庄");
        query.setPageSize(30);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        Log.e("123", "----------------1--" + latLng.latitude);
        PoiSearch poiSearch = new PoiSearch(this, query);
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        //设置查询范围：经纬度、半径、是否从近到远排序（默认是）
        poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 2000, true));
        //设置监听，获取搜索结果
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                poiItems = poiResult.getPois();
                Log.e("123", "----------------i--" + i);
                Log.e("123", "----------------poiItems--" + poiItems.size());
                if (i == 1000) {//i代表响应码
                    mapAdapyer = new MapListAdapter(context, poiItems);
                    lvMapName.setAdapter(mapAdapyer);
                } else {
                    Utils.setToast(context, "onPoiSearched: " + "无搜索结果：" + i);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        //开始异步搜索
        poiSearch.searchPOIAsyn();
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery1(LatLng latLng) {
        int currentPage = 0;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        //PoiSearch.Query query = new PoiSearch.Query("店铺|餐饮|服务|购物|生活|住宿|汽车|休闲|保健|旅游", "", "深圳");
        //第一、二参数为空串好像是搜索所有能搜到的内容
        PoiSearch.Query query = new PoiSearch.Query("住宅区|商务楼", "", "石家庄");
        query.setPageSize(30);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        Log.e("123", "----------------1--");
        Log.e("123", "----------------1--" + latLng.latitude);
        PoiSearch poiSearch = new PoiSearch(this, query);
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        //设置查询范围：经纬度、半径、是否从近到远排序（默认是）
        poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 2000, true));
        //设置监听，获取搜索结果
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                poiItems = poiResult.getPois();
                Log.e("123", "----------------i--" + i);
                Log.e("123", "----------------poiItems--" + poiItems.size());
                if (i == 1000) {//i代表响应码
                    mapAdapyer = new MapListAdapter(context, poiItems);
                    lvMapName.setAdapter(mapAdapyer);
//                    for (int x = 0; x < poiItems.size(); x++) {
//                        Log.e("123", poiItems.get(i).getTitle());
//                    }
//                    mapAdapyer.notifyDataSetChanged();
                } else {
                    Utils.setToast(context, "onPoiSearched: " + "无搜索结果：" + i);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        //开始异步搜索
        poiSearch.searchPOIAsyn();
    }

    //dip和px转换
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 在屏幕中心添加一个Marker
     */
    private void addMarkerInScreenCenter() {
        latLng = aMap.getCameraPosition().target;
        latlng = new LatLng(lat, lon);
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        screenMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        screenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);

    }

    /**
     * maplist  的点击事件
     * 带返回值挺好上一页面关闭当前页面
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.lv_map_name)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //得到地址信息
        toIntention(poiItems.get(position).getSnippet() + poiItems.get(position).getTitle());

    }

    /**
     * 返回修改顾客信息页面
     *
     * @param str 返回地址信息
     */
    private void toIntention(String str) {
        Intent intent = new Intent();
        intent.putExtra("address", str);
        setResult(INTENT_RETURN, intent);
        finish();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    /**
     * 跳转地址模糊查询页面
     */
    @OnClick(R.id.ll_search)
    public void onViewClicked() {
        Intent it = new Intent(this, InputtipsActivity.class);
        startActivityForResult(it, INTENT_KEY);
    }

    /**
     * 跳转地址模糊查询页面
     */
    @OnClick(R.id.iv_map_back)
    public void iv_map_back() {
        finish();
    }

    /**
     * 结束扫描页面返回信息
     *
     * @param requestCode 传入标记值
     * @param resultCode  返回标记值
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case InputtipsActivity.INTENT_RETURN:
                //接受poi查询地址的数据
                String str = data.getStringExtra("address");
                toIntention(str);
            default:
                break;
        }
    }

    @OnClick({R.id.tv_housing_estate, R.id.tv_map_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_housing_estate:
                tvHousingEstate.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvMapAll.setTextColor(getResources().getColor(R.color.black_text));
                doSearchQuery(latLng);
                break;
            case R.id.tv_map_all:
                tvMapAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvHousingEstate.setTextColor(getResources().getColor(R.color.black_text));
                doSearchQuery1(latLng);
                break;
        }
    }
}
