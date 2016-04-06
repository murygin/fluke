/*
 * Copyright 2016 SerNet Service Network GmbH.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.sernet.fluke.gui.vaadin.ui;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public abstract class Note {

    Notification note;

    public static void warning(String caption) {

        new Notification(caption, null,
                Notification.TYPE_WARNING_MESSAGE, true)
                        .show(Page.getCurrent());

    }

    public static void info(String caption) {
        new Notification(caption, null,
                Notification.TYPE_TRAY_NOTIFICATION, true)
                        .show(Page.getCurrent());

    }

    public static void error(String caption) {
        new Notification(caption, null,
                Notification.TYPE_ERROR_MESSAGE, true)
                        .show(Page.getCurrent());

    }

}
