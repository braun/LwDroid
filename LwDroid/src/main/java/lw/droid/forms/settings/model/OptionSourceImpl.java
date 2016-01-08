package lw.droid.forms.settings.model;

import java.util.Arrays;
import java.util.List;

/**
 * Simple handy implementation of OptionSource based on array
 * @author Braun
 *
 */
public class OptionSourceImpl implements OptionSource {

	Option[] options;
	public OptionSourceImpl(Option ...options)
	{
		this.options = options;
	}
	@Override
	public List<Option> getOptions() {
		// TODO Auto-generated method stub
		return Arrays.asList(this.options);
	}

}
