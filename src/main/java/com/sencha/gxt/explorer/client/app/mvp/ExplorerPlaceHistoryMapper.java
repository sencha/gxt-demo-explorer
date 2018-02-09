package com.sencha.gxt.explorer.client.app.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.sencha.gxt.explorer.client.app.place.ExamplePlace;

@WithTokenizers({ ExamplePlace.Tokenizer.class })
public interface ExplorerPlaceHistoryMapper extends PlaceHistoryMapper {
}
