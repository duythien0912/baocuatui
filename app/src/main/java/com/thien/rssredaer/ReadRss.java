package com.thien.rssredaer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



/**
 * Created by thien on 26-05-2018.
 */
public class ReadRss extends AsyncTask<String, Void, Void> {
    Context context;
    ProgressDialog progressDialog;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    URL url;

    public ReadRss(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    //before fetching of rss statrs show progress to user
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }


    //This method will execute in background so in this method download rss feeds
    @Override
    protected Void doInBackground(String... params) {
        //call process xml method to process document we downloaded from getData() method
        ProcessXml(Getdata(params[0]), params[1]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        FeedsAdapter adapter = new FeedsAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(20));
        recyclerView.setAdapter(adapter);
    }

    // In this method we will process Rss feed  document we downloaded to parse useful information from it
    private void ProcessXml(Document data, String kind) {
        if (data != null) {
            feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node cureentchild = items.item(i);
                if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item = new FeedItem();
                    NodeList itemchilds = cureentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node cureent = itemchilds.item(j);
                        if (cureent.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase("description")) {
                            item.setDescription("");
                            if (kind == "24h") {
                                String str = cureent.getTextContent();
                                String[] abc = str.split("(src=')|(' alt=')|(/></a><br />)");
                                String s = abc[3].replace("&amp;#34;","\"");
                                item.setDescription(s);
                                item.setThumbnailUrl(abc[1]);
                            } else if (kind == "vnexpress") {
                                String str = cureent.getTextContent();
                                String[] abc = str.split("(https://)|(\" ></a></br>)");
                                String link = "https://"+abc[2];
                                item.setDescription(abc[3]);
                                item.setThumbnailUrl(link);
                            } else if (kind == "kenh14") {
                                String str = cureent.getTextContent();
                                item.setDescription(str);
                            }
                        } else if (cureent.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPubDate(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase("content:encoded")) {
                            String str = cureent.getTextContent();
                            String htmlTextStr = Html.fromHtml(str).toString();
                            item.setDescription(htmlTextStr);
                    }
                    }
                    feedItems.add(item);
                }
            }
        }
    }

    //This method will download rss feed document from specified url
    public Document Getdata(String address) {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
