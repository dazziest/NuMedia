package com.nu.media.views.listeners;

import android.support.v4.app.Fragment;

public interface OnMenuClickListener{
	public void onClickMainMenu();
	public void onSelectFragmentMenu(Class<? extends Fragment> cls, int pos);
//	public void onSaveAllChanges();
//	public void onResetAll();
	public void onClickMainMenu(Class<? extends Fragment> cls, int position);
}
