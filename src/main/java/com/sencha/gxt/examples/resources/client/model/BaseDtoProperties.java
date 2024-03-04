/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2021, Sencha Inc.
 *
 * licensing@sencha.com
 * http://www.sencha.com/products/gxt/license/
 *
 * ================================================================================
 * Commercial License
 * ================================================================================
 * This version of Sencha GXT is licensed commercially and is the appropriate
 * option for the vast majority of use cases.
 *
 * Please see the Sencha GXT Licensing page at:
 * http://www.sencha.com/products/gxt/license/
 *
 * For clarification or additional options, please contact:
 * licensing@sencha.com
 * ================================================================================
 *
 *
 *
 *
 *
 *
 *
 *
 * ================================================================================
 * Disclaimer
 * ================================================================================
 * THIS SOFTWARE IS DISTRIBUTED "AS-IS" WITHOUT ANY WARRANTIES, CONDITIONS AND
 * REPRESENTATIONS WHETHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, MERCHANTABLE QUALITY,
 * FITNESS FOR A PARTICULAR PURPOSE, DURABILITY, NON-INFRINGEMENT, PERFORMANCE AND
 * THOSE ARISING BY STATUTE OR FROM CUSTOM OR USAGE OF TRADE OR COURSE OF DEALING.
 * ================================================================================
 */
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
