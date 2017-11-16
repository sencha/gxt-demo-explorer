package com.sencha.gxt.examples.resources.client.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface BaseDtoProperties extends PropertyAccess<BaseDto> {

  public final ModelKeyProvider<BaseDto> key = new ModelKeyProvider<BaseDto>() {
    @Override
    public String getKey(BaseDto item) {
      return (item instanceof FolderDto ? "f-" : "m-") + item.getId().toString();
    }
  };

  ValueProvider<BaseDto, String> name();

  public final ValueProvider<BaseDto, String> author = new ValueProvider<BaseDto, String>() {
    @Override
    public String getValue(BaseDto object) {
      return object instanceof MusicDto ? ((MusicDto) object).getAuthor() : "";
    }

    @Override
    public void setValue(BaseDto object, String value) {
      if (object instanceof MusicDto) {
        ((MusicDto) object).setAuthor(value);
      }
    }

    @Override
    public String getPath() {
      return "author";
    }
  };

  public final ValueProvider<BaseDto, String> genre = new ValueProvider<BaseDto, String>() {
    @Override
    public String getValue(BaseDto object) {
      return object instanceof MusicDto ? ((MusicDto) object).getGenre() : "";
    }

    @Override
    public void setValue(BaseDto object, String value) {
      if (object instanceof MusicDto) {
        ((MusicDto) object).setGenre(value);
      }
    }

    @Override
    public String getPath() {
      return "genre";
    }
  };

}
