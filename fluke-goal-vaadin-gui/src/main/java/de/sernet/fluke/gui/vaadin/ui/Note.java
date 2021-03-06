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

import com.vaadin.ui.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public abstract class Note {
    
    Logger logger = LoggerFactory.getLogger(Note.class);

    public static void warning(String caption) {

        Notification.show(caption, Notification.Type.WARNING_MESSAGE);

    }

    public static void info(String caption) {
        Notification.show(caption, Notification.Type.TRAY_NOTIFICATION);

    }

    public static void error(String caption) {
        Notification.show(caption, Notification.Type.ERROR_MESSAGE);
    }

}
