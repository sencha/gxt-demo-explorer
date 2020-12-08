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
package com.sencha.gxt.explorer.client.model;

import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;
import com.sencha.gxt.explorer.client.ExplorerApp;

public abstract class ExampleModel {
  protected List<Category> categories = new ArrayList<Category>();

  public Example findExampleByName(String name) {
    List<Example> list = getExamplesAsList();
    for (int i = 0; i < list.size(); i++) {
      Example ex = list.get(i);
      if (ex.getName().equals(name)) {
        return ex;
      }
    }
    return null;
  }
  
  public Example findExample(String id) {
    List<Example> list = getExamplesAsList();
    for (int i = 0; i < list.size(); i++) {
      Example ex = list.get(i);
      if (ex.getId().equals(id)) {
        return ex;
      }
    }
    return null;
  }

  public List<Example> getExamplesAsList() {
    List<Example> list = new ArrayList<Example>();
    for (int i = 0; i < categories.size(); i++) {
      Category c = categories.get(i);
      for (int j = 0; j < c.getExamples().size(); j++) {
        list.add(c.getExamples().get(j));
      }
    }

    return list;
  }

  protected ExampleModel() {
    // protected to it isn't instantiated directly, except by generated subclass
  }

  public List<Category> getCategories() {
    return categories;
  }

  public List<TreeStore.TreeNode<NamedModel>> getModels() {
    List<TreeStore.TreeNode<NamedModel>> models = new ArrayList<TreeStore.TreeNode<NamedModel>>();
    for (final Category c : categories) {
      if (c.getName().equals(ExplorerApp.OVERVIEW_CATEGORY)) {
        continue;
      }
      final List<TreeStore.TreeNode<NamedModel>> examples = new ArrayList<TreeStore.TreeNode<NamedModel>>();
      for (final Example e : c.getExamples()) {
        examples.add(new TreeStore.TreeNode<NamedModel>() {
          @Override
          public List<? extends TreeNode<NamedModel>> getChildren() {
            return null;
          }

          @Override
          public NamedModel getData() {
            return e;
          }
        });
      }
      models.add(new TreeStore.TreeNode<NamedModel>() {
        @Override
        public List<? extends TreeNode<NamedModel>> getChildren() {
          return examples;
        }

        @Override
        public NamedModel getData() {
          return c;
        }
      });
    }

    return models;
  }

}
