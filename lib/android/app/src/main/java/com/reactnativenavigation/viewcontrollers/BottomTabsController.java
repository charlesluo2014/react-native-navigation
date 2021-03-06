package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.reactnativenavigation.utils.CompatUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.RelativeLayout.ABOVE;
import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;

public class BottomTabsController extends ParentController implements BottomNavigationView.OnNavigationItemSelectedListener {
	private BottomNavigationView bottomNavigationView;
	private List<ViewController> tabs = new ArrayList<>();
	private int selectedIndex = 0;

	public BottomTabsController(final Activity activity, final String id) {
		super(activity, id);
	}

	@NonNull
	@Override
	protected ViewGroup createView() {
		RelativeLayout root = new RelativeLayout(getActivity());
		bottomNavigationView = new BottomNavigationView(getActivity());
		bottomNavigationView.setId(CompatUtils.generateViewId());
		bottomNavigationView.setBackgroundColor(Color.DKGRAY);
		bottomNavigationView.setOnNavigationItemSelectedListener(this);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
		lp.addRule(ALIGN_PARENT_BOTTOM);
		root.addView(bottomNavigationView, lp);
		return root;
	}

	@Override
	public boolean handleBack() {
		return !tabs.isEmpty() && tabs.get(selectedIndex).handleBack();
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
		selectTabAtIndex(item.getItemId());
		return true;
	}

	public void selectTabAtIndex(final int newIndex) {
		tabs.get(selectedIndex).getView().setVisibility(View.GONE);
		selectedIndex = newIndex;
		tabs.get(selectedIndex).getView().setVisibility(View.VISIBLE);
	}

	public void setTabs(final List<ViewController> tabs) {
		if (tabs.size() > 5) {
			throw new RuntimeException("Too many tabs!");
		}
		this.tabs = tabs;
		getView();
		for (int i = 0; i < tabs.size(); i++) {
			String title = String.valueOf(i);
			createTab(tabs.get(i), i, title);
		}
		selectTabAtIndex(0);
	}

	private void createTab(ViewController tab, final int index, final String title) {
		bottomNavigationView.getMenu().add(0, index, Menu.NONE, title);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
		params.addRule(ABOVE, bottomNavigationView.getId());
		tab.getView().setVisibility(View.GONE);
		getView().addView(tab.getView(), params);
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	@Override
	public Collection<ViewController> getChildControllers() {
		return tabs;
	}
}
