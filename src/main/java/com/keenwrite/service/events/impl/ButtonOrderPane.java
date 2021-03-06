/*
 * Copyright 2020 White Magic Software, Ltd.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.keenwrite.service.events.impl;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;

import static com.keenwrite.Constants.SETTINGS;
import static javafx.scene.control.ButtonBar.BUTTON_ORDER_WINDOWS;

/**
 * Ensures a consistent button order for alert dialogs across platforms (because
 * the default button order on Linux defies all logic).
 */
public class ButtonOrderPane extends DialogPane {

  @Override
  protected Node createButtonBar() {
    final var node = (ButtonBar) super.createButtonBar();
    node.setButtonOrder( getButtonOrder() );
    return node;
  }

  private String getButtonOrder() {
    return getSetting( "dialog.alert.button.order.windows",
                       BUTTON_ORDER_WINDOWS );
  }

  @SuppressWarnings("SameParameterValue")
  private String getSetting( final String key, final String defaultValue ) {
    return SETTINGS.getSetting( key, defaultValue );
  }
}
