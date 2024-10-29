package com.sanskruti.volotek.ui.activities;

import static com.sanskruti.volotek.utils.MyUtils.preferenceManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.sanskruti.volotek.R;
import com.sanskruti.volotek.adapters.SearchAdapter;
import com.sanskruti.volotek.databinding.ActivitySearchBinding;
import com.sanskruti.volotek.databinding.ActivitySearchGlobalBinding;
import com.sanskruti.volotek.model.CategoryItem;
import com.sanskruti.volotek.model.SearchDataObj;
import com.sanskruti.volotek.ui.fragments.MyBottomSheetFragment;
import com.sanskruti.volotek.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class GlobalSearch extends AppCompatActivity{

    ActivitySearchGlobalBinding binding;
    List<SearchDataObj> postItemList;
    private StaggeredGridLayoutManager layoutManager;

    private Boolean isGreeting;

    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_global);
        binding = ActivitySearchGlobalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.toolName.setText("Search");
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        Constant.getHomeViewModel(this).getSearchData()
                .observe(this,searchData -> {
            if(searchData != null){
                Log.i("saqlain",searchData.getMessage());

                postItemList = searchData.getData();

                adapter = new SearchAdapter(this, (data) -> {
                    if (postItemList != null) {

                        isGreeting = adapter.getOriginalList().get(data).getType().equals("greeting_post") ? true :false;

                        MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment(adapter.getOriginalList().get(data).path,this,"NA",isGreeting,
                                "",adapter.getOriginalList().get(data).getPosition(),false);

                        // Or using static method
                        // MyBottomSheetFragment bottomSheetFragment = MyBottomSheetFragment.newInstance(itemData);

                        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                    }
                }, 3, getResources().getDimension(com.intuit.ssp.R.dimen._2ssp));
                binding.searchItem.setAdapter(adapter);
                adapter.setPosts(postItemList);
                layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

                binding.searchItem.setLayoutManager(layoutManager);
            }
        });

        editTextSearchBar();
    }

    private void editTextSearchBar() {
        binding.etSearch.setOnEditorActionListener((textView, i, keyEvent) -> {

            if (i == EditorInfo.IME_ACTION_SEARCH) {

                Toast.makeText(this, "category " + "Sanskruti", Toast.LENGTH_SHORT).show();

                if (binding.etSearch.getText() != null && !binding.etSearch.getText().toString().isEmpty()) {

                    filter(binding.etSearch.getText().toString());


                } else {
                    Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show();
                }


            }

            return false;
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //code
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                filter(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {
                //code
            }
        });
    }

    void filter(String text) {

        ArrayList<SearchDataObj> temp = new ArrayList();

        for (SearchDataObj d : postItemList) {

            if (d != null) {
                if (d.getCategory().toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d);
                }
            }

        }
        adapter.setPosts(temp);
    }

}
