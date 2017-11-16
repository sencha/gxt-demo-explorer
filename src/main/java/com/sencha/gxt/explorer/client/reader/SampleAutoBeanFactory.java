package com.sencha.gxt.explorer.client.reader;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface SampleAutoBeanFactory extends AutoBeanFactory {
  AutoBean<ItemsResult> jsonItems();
}