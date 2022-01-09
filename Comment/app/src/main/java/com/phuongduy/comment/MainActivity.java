package com.phuongduy.comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;

import com.phuongduy.comment.adapter.CommentAdapter;
import com.phuongduy.comment.databinding.ActivityMainBinding;
import com.phuongduy.comment.factory.MainViewModelFactory;
import com.phuongduy.comment.presentation.MainViewModel;
import com.phuongduy.comment.view.DefaultListVerticalDivider;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding viewBinding;
    private MainViewModel viewModel;
    private CommentAdapter commentAdapter;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = MainViewModelFactory.create();

        setContentView(viewBinding.getRoot());

        setUpUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        viewModel.cleanUp();
    }

    private void setUpUI() {
        commentAdapter = new CommentAdapter();
        viewBinding.commentList.setAdapter(commentAdapter);
        viewBinding.commentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        viewBinding.commentList.addItemDecoration(new DefaultListVerticalDivider());

        viewBinding.edtComment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                Editable editable = viewBinding.edtComment.getText();
                if (editable != null) {
                    viewModel.addComment(editable.toString());
                    viewBinding.edtComment.getText().clear();
                }
            }
            return true;
        });

        compositeDisposable.add(viewModel.commentItemList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentUiModels -> {
                    commentAdapter.updateList(commentUiModels);
                    int listSize = commentAdapter.getItemCount();
                    if (listSize > 0) {
                        viewBinding.commentList.scrollToPosition(listSize - 1);
                    }
                }));
    }
}