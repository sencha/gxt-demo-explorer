/**
 * Sencha GXT 1.1.0-SNAPSHOT - Sencha for GWT
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

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.Splittable;
import com.google.web.bindery.autobean.shared.impl.StringQuoter;

public class ExampleData {

  public interface DataBundle extends ClientBundle {

    @Source("browser.json")
    TextResource browserData();
    
    @Source("stock.json")
    TextResource stockData();

    @Source("country.json")
    TextResource countryData();

    @Source("state.json")
    TextResource stateData();

    @Source("plant.json")
    TextResource plantData();

    @Source("task.json")
    TextResource taskData();
  }
  
  public interface BrowserResult {
    List<BrowserProxy> getBrowserData();
  }

  public interface StockResult {
    List<StockProxy> getStocks();
  }
  public interface CountryResult {
    List<CountryProxy> getCountries();
  }
  public interface StateResult {
    List<StateProxy> getStates();
  }
  public interface PlantResult {
    List<PlantProxy> getPlants();
  }
  public interface TaskResult {
    List<TaskProxy> getTasks();
  }

  public interface ExampleDataFactory extends AutoBeanFactory {
    AutoBean<BrowserResult> browserResult();
    
    AutoBean<StockResult> stockResult();

    AutoBean<CountryResult> countryResult();

    AutoBean<StateResult> stateResult();

    AutoBean<PlantResult> plantResult();

    AutoBean<TaskResult> taskResult();

  }

  public static DataBundle dataBundle = GWT.create(DataBundle.class);
  public static ExampleDataFactory dataFactory = GWT.create(ExampleDataFactory.class);

  public static List<BrowserProxy> getBrowserData() {
    Splittable s = StringQuoter.split(dataBundle.browserData().getText());
    AutoBean<BrowserResult> result = AutoBeanCodex.decode(dataFactory, BrowserResult.class, s);
    return result.as().getBrowserData();
  }
  
  public static List<StockProxy> getStocks() {
    Splittable s = StringQuoter.split(dataBundle.stockData().getText());
    AutoBean<StockResult> result = AutoBeanCodex.decode(dataFactory, StockResult.class, s);
    return result.as().getStocks();
  }

  public static List<CountryProxy> getCountries() {
    Splittable s = StringQuoter.split(dataBundle.countryData().getText());
    AutoBean<CountryResult> result = AutoBeanCodex.decode(dataFactory, CountryResult.class, s);
    return result.as().getCountries();
  }

  public static List<StateProxy> getStates() {
    Splittable s = StringQuoter.split(dataBundle.stateData().getText());
    AutoBean<StateResult> result = AutoBeanCodex.decode(dataFactory, StateResult.class, s);
    return result.as().getStates();
  }

  public static List<PlantProxy> getPlants() {
    Splittable s = StringQuoter.split(dataBundle.plantData().getText());
    AutoBean<PlantResult> result = AutoBeanCodex.decode(dataFactory, PlantResult.class, s);
    return result.as().getPlants();
  }

  public static List<TaskProxy> getTasks() {
    Splittable s = StringQuoter.split(dataBundle.taskData().getText());
    AutoBean<TaskResult> result = AutoBeanCodex.decode(dataFactory, TaskResult.class, s);
    return result.as().getTasks();
  }

}
