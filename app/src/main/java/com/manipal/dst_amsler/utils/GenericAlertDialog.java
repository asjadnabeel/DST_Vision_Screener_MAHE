package com.manipal.dst_amsler.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class GenericAlertDialog
{
	public static void showDialog(Context where, String message)
	{
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(where);
		alertDialog.setMessage(message);
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

			}
		});
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

			}
		});
		alertDialog.show();
	}
}
