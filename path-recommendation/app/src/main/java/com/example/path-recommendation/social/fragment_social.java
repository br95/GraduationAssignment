package com.example.shareonfoot.social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.shareonfoot.R;
import com.example.shareonfoot.home.activity_home;
import com.example.shareonfoot.util.OnBackPressedListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.tabs.TabLayout;

import static android.app.Activity.RESULT_OK;

public class fragment_social extends Fragment implements OnBackPressedListener {

    ViewGroup viewGroup;
    Toast toast;
    long backKeyPressedTime;
    int ADD_BOARD = 8080;
    Activity activity;
    private TabLayout tabLayout;
    private ViewPager finalPager;

    //RelativeLayout filterButton;
    //RelativeLayout addButton;

    //DrawerLayout drawer;
    //SlidingDrawer slidingDrawer;
    //LinearLayout drawer_content;

    //TextView tv_add_image;
    //TextView tv_from_codi;

    private FloatingActionMenu fam;
    //private FloatingActionButton fabAdd, fabBring;

    public static fragment_social newInstance() {

        Bundle args = new Bundle();

        fragment_social fragment = new fragment_social();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_social,container,false);
        toast = Toast.makeText(getContext(),"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);
        return viewGroup;
    }

    //액티비티에 재부착될 때의 처리.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity = (Activity) context;
            ((activity_home)activity).setOnBackPressedListener(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        //addButton = getView().findViewById(R.id.header_add);
        //filterButton = getView().findViewById(R.id.header_search);

//        drawer = getView().findViewById(R.id.final_drawer_layout);
//        slidingDrawer = getView().findViewById(R.id.sliding_drawer);
//        drawer_content = getView().findViewById(R.id.drawer_content);

        //tv_add_image = getView().findViewById(R.id.tv_add_image);
        //tv_from_codi= getView().findViewById(R.id.tv_from_codi);



/*
        BtnOnClickListener onClickListener = new BtnOnClickListener();
*/
        //addButton.setOnClickListener(onClickListener);
        //tv_add_image.setOnClickListener(onClickListener);
        //tv_from_codi.setOnClickListener(onClickListener);
        //drawer_content.setOnClickListener(onClickListener);

        //NavigationView navigationView = (NavigationView) getView().findViewById(R.id.final_nav_view); //드로워 뷰


        //필터 버튼 클릭하면 드로워 열고 닫기
//        filterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(drawer.isDrawerOpen(GravityCompat.START)) {
//                    drawer.closeDrawer(GravityCompat.START);
//                } else {
//                    drawer.openDrawer(GravityCompat.START);
//                }
//            }
//        });

        //필터(메뉴) 아이템 선택
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId())
//                {
//                    case R.id.menuitem1:
//                        Toast.makeText(getContext(), "SelectedItem 1", Toast.LENGTH_SHORT).show();
//                    case R.id.menuitem2:
//                        Toast.makeText(getContext(), "SelectedItem 2", Toast.LENGTH_SHORT).show();
//                    case R.id.menuitem3:
//                        Toast.makeText(getContext(), "SelectedItem 3", Toast.LENGTH_SHORT).show();
//                }
//
//                DrawerLayout drawer = getView().findViewById(R.id.final_drawer_layout);
//                //drawer.closeDrawer(GravityCompat.START);
//                return true;
//            }
//        });

        if(tabLayout == null){
            //탭 목록 설정
            tabLayout = (TabLayout) getView().findViewById(R.id.tabLayout);
            tabLayout.addTab(tabLayout.newTab().setText("팔로잉"));
            tabLayout.addTab(tabLayout.newTab().setText("인기"));
            tabLayout.addTab(tabLayout.newTab().setText("최신"));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            //탭 페이저 설정 (탭 클릭시 바뀌는 화면)
            finalPager = (ViewPager) getView().findViewById(R.id.tab_Pager);

            finalPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    finalPager.setCurrentItem(tab.getPosition());
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }
                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }




        //플로팅 액션 버튼 설정
        //fabAdd = (FloatingActionButton) viewGroup.findViewById(R.id.fab_add_photo);
        //fabBring = (FloatingActionButton) viewGroup.findViewById(R.id.fab_bring_codibook);
     /*   fam = (FloatingActionMenu) viewGroup.findViewById(R.id.fab_menu);

        //handling menu status (open or close)
        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    //Toast.makeText(getContext(), "Menu is opened", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getContext(), "Menu is closed", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
        //handling each floating action button clicked
        //fabAdd.setOnClickListener(onClickListener);
        //fabBring.setOnClickListener(onClickListener);

    /*    fam.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
        //fam.open(true);
        //fam.close(true);
        //fam.setClosedOnTouchOutside(true);





    }


    @Override
    public void onResume() {
        super.onResume();
        //activity.setOnBackPressedListener(this);
    }

    //뒤로 가기 버튼이 눌렸을 경우 드로워(메뉴)를 닫는다.
    @Override
    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else if (slidingDrawer.isOpened()) {
//            slidingDrawer.close();
//        }
//        else
            if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        } else if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            activity.finish();
            toast.cancel();
        }

    }

    //클릭 리스너
/*
    class BtnOnClickListener implements Button.OnClickListener {
        String res="";

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.header_add : //헤더- 추가 버튼
//                    //slidingDrawer.open();
//                    break;
//                case R.id.fab_add_photo:
//                    Intent intent = new Intent(getContext(), activity_addBoard.class);
//                    startActivityForResult(intent,ADD_BOARD);
//                    break;
//                case R.id.fab_bring_codibook:
//                    //
//                    break;
//                case R.id.tv_add_image :
//                    Intent intent = new Intent(getContext(), activity_addBoard.class);
//                    startActivityForResult(intent,ADD_BOARD);
//                    break;
//                case R.id.tv_from_codi :
//                    break;
//                case R.id.drawer_content :
//                    slidingDrawer.close();
//                    break;
            }
        }
    }
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_BOARD && resultCode == RESULT_OK)
            ((activity_home)activity).refresh_share();
    }


}