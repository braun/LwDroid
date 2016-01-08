package lw.droid.forms.settings.model;

public class OptionImpl implements Option {
	String mDisplay = "";
	String mValue = "";

	MetaSettings subSettings;
	

	public OptionImpl(String mDisplay, String mValue) {
		super();
		this.mDisplay = mDisplay;
		this.mValue = mValue;
	}
	public OptionImpl(String mDisplay, String mValue,MetaSettings subSettings) {
		super();
		this.mDisplay = mDisplay;
		this.mValue = mValue;
		this.subSettings = subSettings;
	}

	public MetaSettings getSubSettings() {
		return subSettings;
	}
	/**
	 * @return the mDisplay
	 */
	public String getDisplay() {
		return mDisplay;
	}

	/**
	 * @return the mValue
	 */
	public String getValue() {
		return mValue;
	}
}