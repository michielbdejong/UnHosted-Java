/**
 * 
 */
package org.unhosted;

import java.net.*;
import java.util.regex.*;

import org.unhosted.html5.Storage;


/**
 * @author piranna
 *
 */
public class OAuth
{
	// Storage namespace
	final private String namespace = "OAuth2-cs";

	// Attributes
	private Storage localStorage;
	private URL location;

	// Constructor
	public OAuth(Storage localStorage)
	{
		this.localStorage = localStorage;

		this.receiveToken();
	}

	public URL dance(String oAuthDomain, String userName, String app)
	throws MalformedURLException
	{
		return new URL(oAuthDomain+"oauth2/auth?response_type=token"
				+"&scope="+this.location.getAuthority()
				+"&user_name="+userName
				+"&client_id="+app+"&redirect_uri="+app);
	}

	// Token
	public void receiveToken()
	{
		Pattern pattern = Pattern.compile("[\\?&]token=([^&#]*)");
		Matcher matcher = pattern.matcher(this.location.toString());
		if(matcher.find())
		{
			this.localStorage.setItem(this.namespace+"::token", matcher.group(1));

			try
			{
				this.location = new URL(this.location.toString().split("?")[0]);
			}
			catch(MalformedURLException e)
			{
				e.printStackTrace();
			};
		}
	}

	public String getToken()
	{
		return (String)this.localStorage.getItem(this.namespace+"::token");
	}

	public void revokeToken()
	{
		this.localStorage.removeItem(this.namespace+"::token");
	}
}