package ru.terra.spending.core.network;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import ru.terra.spending.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class DownloadManager
{
	private ProgressDialog progressDialog;
	private Activity activity;
	private File cacheDir;

	private class DownloadFile extends AsyncTask<String, Integer, String>
	{

		private WorkIsDoneListener wis;

		public DownloadFile(WorkIsDoneListener w)
		{
			this.wis = w;
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected void onProgressUpdate(Integer... progress)
		{
			super.onProgressUpdate(progress);
			progressDialog.setProgress(progress[0]);
		}

		@Override
		protected String doInBackground(String... sUrl)
		{
			try
			{
				URL url = new URL(sUrl[0]);
				URLConnection connection = url.openConnection();
				connection.connect();
				// this will be useful so that you can show a typical 0-100% progress bar
				int fileLength = connection.getContentLength();

				// download the file
				InputStream input = new BufferedInputStream(url.openStream());
				if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
					cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), activity.getString(R.string.cache_dir));
				else
					cacheDir = activity.getCacheDir();
				if (!cacheDir.exists())
					cacheDir.mkdirs();
				String filename = url.getPath().substring(url.getPath().lastIndexOf("/"));
				OutputStream output = new FileOutputStream(cacheDir + "/" + filename);

				byte data[] = new byte[1024];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1)
				{
					total += count;
					// publishing the progress....
					publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}
				// long total = 0;
				// ByteArrayBuffer baf = new ByteArrayBuffer(50);
				// int current = 0;
				// while ((current = input.read()) != -1)
				// {
				// total++;
				// baf.append((byte) current);
				// publishProgress((int) (total * 100 / fileLength));
				// }
				//
				// output.write(baf.toByteArray());
				output.flush();
				output.close();
				input.close();
			} catch (Exception e)
			{
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			progressDialog.dismiss();
			if (wis != null)
			{
				wis.workIsDone(String.valueOf(ActivityConstants.DOWNLOAD_FINISHED));
			}
		}

	}

	public DownloadManager(Activity a, String url, WorkIsDoneListener wis)
	{
		this.activity = a;
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Загрузка...");
		progressDialog.setIndeterminate(false);
		progressDialog.setMax(100);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		DownloadFile downloadFile = new DownloadFile(wis);
		downloadFile.execute(url);
	}
}
