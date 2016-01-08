package lw.droid.forms.settings;

import android.content.Context;
import lw.droid.forms.settings.model.MetaProperty;

/**
 * abstract factory for creating  particular editor of MetaPropery
 *  You specify type of editor by choosing concrete factory in MetaProperty constructor
 * @author Braun
 *
 */
public interface PropertyViewFactory {
	PropertyView createView(Context ctx,MetaProperty property);
}
