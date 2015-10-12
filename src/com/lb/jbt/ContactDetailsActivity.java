package com.lb.jbt;

import java.util.ArrayList;
import java.util.List;

import com.lb.entities.Friend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ContactDetailsActivity extends Activity implements
		View.OnClickListener {
	private final Integer REQUEST_CONTACTS_DETAILS_CODE = 2;
	private ImageView right_menu_btn;
	private ExpandableListView contact_details_lv;
	// email and phone
	private List<String> detailsTitles = null;
	// email contents and phone contents
	private List<List<String>> detailsContents = null;
	private ContactDetailExpandableAdapter adapter = null;
	// passed friend
	private Friend passFriend = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_details);
		detailsTitles = new ArrayList<String>();
		detailsTitles.add(getResources().getString(R.string.phone));
		detailsTitles.add(getResources().getString(R.string.emial));
		detailsContents = new ArrayList<List<String>>();
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("friendBundle");
		passFriend = (Friend) bundle.get("passFriend");
		detailsContents.add(passFriend.getPhones());
		detailsContents.add(passFriend.getEmails());
		System.out.println(detailsTitles.toString() + "---------"
				+ detailsContents.toString());
		right_menu_btn = (ImageView) findViewById(R.id.right_menu_btn);
		contact_details_lv = (ExpandableListView) findViewById(R.id.contact_details_lv);
		right_menu_btn.setOnClickListener(this);
		adapter = new ContactDetailExpandableAdapter(
				ContactDetailsActivity.this, detailsTitles, detailsContents);
		contact_details_lv.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.right_menu_btn:
			ContactDetailsActivity.this.finish();
			break;

		default:
			break;
		}
	}

	private class ContactDetailExpandableAdapter extends
			BaseExpandableListAdapter {
		private Context context;
		private List<String> titles;
		private List<List<String>> contents;

		public ContactDetailExpandableAdapter(Context context,
				List<String> titles, List<List<String>> contents) {
			super();
			this.context = context;
			this.titles = titles;
			this.contents = contents;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return contents.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.contact_details_contents, null);
			}
			TextView content_item = (TextView) convertView
					.findViewById(R.id.content_item);
			content_item
					.setText(contents.get(groupPosition).get(childPosition));
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return contents.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return getGroup(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return titles.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.contact_details_title, null);
			}
			CheckedTextView ctv = (CheckedTextView) convertView
					.findViewById(R.id.detail_title_item);
			ctv.setText(titles.get(groupPosition));
			ctv.setClickable(isExpanded);
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}

	}
}
