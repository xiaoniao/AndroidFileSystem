package com.liuzhuangzhuang.file;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FileListFragment.OnListFragmentInteractionListener {

    private int order = 0;
    private FileListFragment fragment;
    private SparseArray<List<String>> sparseArray; // TODO 修改类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = (FileListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        DirHelper dirHelper = new DirHelper();
        sparseArray = dirHelper.getDirectoryStructure();

        refresh();
    }

    @Override
    public void onListFragmentInteraction(String item) {
        if (order >= sparseArray.size() - 1) return;

        order++;

        refresh();
    }

    private void refresh() {
        List<String> list = sparseArray.get(order);
        if (list == null) return;
        fragment.setList(list);
        fragment.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && order > 0) {
            order--;
            refresh();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
