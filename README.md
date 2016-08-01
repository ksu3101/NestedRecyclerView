# NestedRecyclerView
Nested RecyclerView example project.

### 1. Description  
- Header, Body, Footer 로 이루어진 Multiple ViewType holder pattern과 `GridLayoutManager`를 등록한 RecyclerView 의 구현 예 프로젝트. 
 - Header : 없어도 상관 없으며 최 상단에서 등장. 예제에서는 [Infinite loop ViewPager](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/viewpagers/InfiniteViewPager.java)를 가지고 있으며 내부에서 또 `Fragment`들을 관리 한다. 
 - Section Header : 없어도 상관 없으며 예제에서는 텍스트 뷰 하나만 존재 한다. 
 - Full width Body : Span 2의 full width를 사용 하는 1 Body row. 
 - Half width Body : Span 1의 half width를 사용 하는 1 Body row/column. 
 - Footer : 없어도 상관 없으며 ViewType에 대응 하는 데이터 타입을 구성 해야 한다. 
 - LoadMore  : `getItem(int position)`했을 경우 결과가 `-1`이라면 load more footer를 설정 하고 보여 준다. 

### 2. Used modules
1. [Generic data type re-usable RecyclerView Adapter](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/SwRecyclerViewAdapter.java)  
2. [Infinite loop ViewPager](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/viewpagers/InfiniteViewPager.java), [ViewPager Indicator](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/viewpagers/pagerindicator/ViewPagerIndicator.java) 
3. MVP pattern, RecyclerView-CardView, RxAndroid, Picasso  

### 3. Todo   
- [x] Header에 sliding ViewPager, ViewPager indicator 적용.  
- [x] Body에 Nested RecyclerView(Horizontal, CardView)적용. 
- [x] apply static Bodys.    
- [x] apply `ItemDecorations`. 
- [x] enable `Load More`pattern. [link](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/MainActivity.java#L100), [Adapter methods](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/list/MainRvAdapter.java#L172)
- [x] apply `Empty View`. 
- [x] enable `Snappy` Horizontal RecyclerView scroll(fling). [참고](http://stackoverflow.com/questions/26370289/snappy-scrolling-in-recyclerview)

### 4. guide
 1. MVP / [Parent classes](https://github.com/ksu3101/NestedRecyclerView/tree/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/mvp)
 2. MVP / VIEW / [MainActivity](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/MainActivity.java) 
 3. MVP / Presenter / [MainActivityPresenter](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/MainActivityPresenter.java)
 4. MVP / Model / [model packaged](https://github.com/ksu3101/NestedRecyclerView/tree/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/list/model)
 5. [Grid RecyclerView](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/MainActivity.java#L60)
 6. Grid RecyclerView / [Adapter](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/list/MainRvAdapter.java) / [Grid LayoutManager](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/MainActivity.java#L63) / [Item Decoration](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/list/MainRvItemDecoration.java)
 7. SwRecyclerView / [Observable Data changed RecyclerView](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/rvs/SwRecyclerView.java) / [Generic data type Adapter](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/rvs/SwRecyclerViewAdapter.java) / [Snappy item classes](https://github.com/ksu3101/NestedRecyclerView/tree/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/rvs/snappy)
 8. ViewPager / [classes](https://github.com/ksu3101/NestedRecyclerView/tree/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/viewpagers) / [Indicator](https://github.com/ksu3101/NestedRecyclerView/tree/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/viewpagers/pagerindicator) / [Implement Header of Main RecyclerView](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/list/MainRvAdapter.java#L83)
 9. Sub Horizontal [RecyclerView Implement](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/list/MainRvAdapter.java#L100) / [Adapter](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/list/SectionRvAdapter.java) / [Item decoration](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/main/list/SubHorRvItemDecoration.java)

![guide image](https://github.com/ksu3101/TIL/blob/master/Android/images/nested_rv_guide.png)

