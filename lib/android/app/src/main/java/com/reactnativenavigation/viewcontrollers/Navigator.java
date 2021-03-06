package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.reactnativenavigation.utils.CompatUtils;

import java.util.Collection;
import java.util.Collections;

public class Navigator extends ParentController {

	private ViewController root;

	public Navigator(final Activity activity) {
		super(activity, "navigator" + CompatUtils.generateViewId());
	}

	@NonNull
	@Override
	protected ViewGroup createView() {
		return new FrameLayout(getActivity());
	}

	@Override
	public Collection<ViewController> getChildControllers() {
		return root == null ? Collections.<ViewController>emptyList() : Collections.singletonList(root);
	}

	@Override
	public boolean handleBack() {
		return root != null && root.handleBack();
	}

	/*
	 * Navigation methods
	 */

	public void setRoot(final ViewController viewController) {
		if (root != null) {
			root.destroy();
		}

		root = viewController;
		getView().addView(viewController.getView());
	}

	public void push(final String fromId, final ViewController viewController) {
		ViewController from = findControllerById(fromId);
		if (from != null) {
			StackController parentStackController = from.getParentStackController();
			if (parentStackController != null) {
				parentStackController.push(viewController);
			}
		}
	}

	public void pop(final String fromId) {
		ViewController from = findControllerById(fromId);
		if (from != null) {
			StackController parentStackController = from.getParentStackController();
			if (parentStackController != null) {
				parentStackController.pop();
			}
		}
	}

	public void popSpecific(final String id) {
		ViewController from = findControllerById(id);
		if (from != null) {
			StackController parentStackController = from.getParentStackController();
			if (parentStackController != null) {
				parentStackController.popSpecific(from);
			}
		}
	}

	public void popToRoot(final String id) {
		ViewController from = findControllerById(id);
		if (from != null) {
			StackController parentStackController = from.getParentStackController();
			if (parentStackController != null) {
				parentStackController.popToRoot();
			}
		}
	}

	public void popTo(final String containerId) {
		ViewController target = findControllerById(containerId);
		if (target != null) {
			StackController parentStackController = target.getParentStackController();
			if (parentStackController != null) {
				parentStackController.popTo(target);
			}
		}
	}
}
