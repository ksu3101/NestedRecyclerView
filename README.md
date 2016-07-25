# NestedRecyclerView
Nested RecyclerView example project.

### 1. Description  
- Header, Body, Footer 로 이루어진 RecyclerView의 구현.  
- MVP패턴 적용. RxAndroid사용. 

### 2. Used modules
1. [Generic data type re-usable RecyclerView Adapter](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/SwRecyclerViewAdapter.java)  
2. [Infinite loop ViewPager](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/viewpagers/InfiniteViewPager.java), [ViewPager Indicator](https://github.com/ksu3101/NestedRecyclerView/blob/master/app/src/main/java/kr/swkang/nestedrecyclerview/utils/viewpagers/pagerindicator/ViewPagerIndicator.java) 
3. RecyclerView-CardView, RxAndroid, Picasso  

### 3. Todo   
- [x] Header에 sliding ViewPager, ViewPager indicator 적용.  
- [x] Body에 Nested RecyclerView(Horizontal, CardView)적용. 
- [x] apply static Bodys.    
- [x] apply ItemDecorations. 
- [ ] enable `Load More`pattern. 

### 4. guide
![guide image](https://github.com/ksu3101/TIL/blob/master/Android/images/nested_rv_guide.png)

