/*
 * Copyright (C) 2015 Mazhar Morshed
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.If not, see <http://www.gnu.org/licenses/>.
 */

package mzhr.librenote.models;

/**
 * Simple class for holding data for a paired text, main and sub for menus and listing
 */
public class SubtextItem {

    private String mainText;
    private String subText;

    public SubtextItem(String mainText, String subText) {
        this.mainText = mainText;
        this.subText = subText;
    }

    public String getMainText() { return mainText; }

    public void setMainText(String mainText) { this.mainText = mainText; }

    public String getSubText() { return subText; }

    public void setSubText(String subText) { this.subText = subText;}

}
