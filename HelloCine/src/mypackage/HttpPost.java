package mypackage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import net.rim.blackberry.api.browser.URLEncodedPostData;
import net.rim.device.api.system.WLANInfo;

public class HttpPost {
	private String _url;
	private URLEncodedPostData _postData;
	private HttpConnection _httpConnection;
	DataInputStream din = null;
	byte[] data = null;

	public HttpPost() {
	}

	public HttpPost(String url) {
		_url = url;
		try {

			if (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED) {
				_httpConnection = (HttpConnection) Connector.open(_url
						+ ";interface=wifi", Connector.READ_WRITE, true);
			} else {
				_httpConnection = (HttpConnection) Connector.open(_url
						+ ";deviceside=true;ConnectionTimeout=360",
						Connector.READ_WRITE, true);
			}

			_httpConnection.setRequestProperty("User-Agent",
					"Profile/MIDP-2.0 Configuration/CLDC-1.0");
			// _httpConnection.setRequestProperty(“Accept”,”application/json,
			// text/javascript, */*”); //for return json
			_httpConnection.setRequestProperty("Content-Language",
					"en-us,en;q=0.5");
			_httpConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			_httpConnection.setRequestMethod(HttpConnection.GET); // default
		} catch (Exception e) {
		}
	}

	public HttpPost(String url, URLEncodedPostData data, String Method) {
		_postData = data;
		_url = url;
		try {
			if (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED) {
				_httpConnection = (HttpConnection) Connector.open(_url
						+ ";interface=wifi", Connector.READ_WRITE, true);
			} else {
				_httpConnection = (HttpConnection) Connector.open(_url
						+ ";deviceside=true;ConnectionTimeout=360",
						Connector.READ_WRITE, true);
			}
			_httpConnection.setRequestProperty("User-Agent",
					"Profile/MIDP-2.0 Configuration/CLDC-1.0");
			_httpConnection.setRequestProperty("Content-Language",
					"en-us,en;q=0.5");
			_httpConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			if (Method.toUpperCase().equals("POST")) {
				_httpConnection.setRequestMethod(HttpConnection.POST);
			} else {
				_httpConnection.setRequestMethod(Method);
			}
		} catch (Exception e) {
		}
	}
	
		
	public String executeMethodGET() {
		InputStream is = null;
		StringBuffer sb = new StringBuffer();
		try {

			is = _httpConnection.openInputStream(); // transition to connected!
			// read data by loop one character
			int ch = 0;
			for (int ccnt = 0; ccnt < 999999; ccnt++) { // get the title.
				ch = is.read();
				if (ch == -1) {
					break;
				}
				sb.append((char) ch);
			}

		} catch (IOException x) {
			// x.printStackTrace();
			System.out.print(x.getMessage());
			return x.getMessage();
		} finally {
			try {
				is.close();
				_httpConnection.close();
			} catch (IOException x) {
				x.printStackTrace();
			}
		}

		return (new String(sb));
	}

}