/**********************************************************************
 * $Source: /cvsroot/jameica/jameica/src/de/willuhn/jameica/gui/boxes/AbstractBox.java,v $
 * $Revision: 1.1 $
 * $Date: 2006/06/29 23:10:01 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.gui.boxes;

import de.willuhn.jameica.system.Settings;

/**
 * Abstrakte Basis-Klasse aller Boxen.
 */
public abstract class AbstractBox implements Box
{
  private static Settings settings = new Settings(Box.class);

  /**
   * ct.
   */
  public AbstractBox()
  {
  }

  /**
   * @see de.willuhn.jameica.gui.boxes.Box#isEnabled()
   */
  public boolean isEnabled()
  {
    return settings.getBoolean(this.getClass().getName() + ".enabled",getDefaultEnabled());
  }

  /**
   * @see de.willuhn.jameica.gui.boxes.Box#setEnabled(boolean)
   */
  public void setEnabled(boolean enabled)
  {
    settings.setAttribute(this.getClass().getName() + ".enabled",enabled);
  }

  /**
   * @see de.willuhn.jameica.gui.boxes.Box#getIndex()
   */
  public int getIndex()
  {
    return settings.getInt(this.getClass().getName() + ".index",getDefaultIndex());
  }

  /**
   * @see de.willuhn.jameica.gui.boxes.Box#setIndex(int)
   */
  public void setIndex(int index)
  {
    settings.setAttribute(this.getClass().getName() + ".index",index);
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object arg0)
  {
    if (arg0 == null || !(arg0 instanceof Box))
      return 1;
    Box other = (Box) arg0;
    
    int index = getIndex();
    int oindex = other.getIndex();
    if (index == oindex)
      return 0;
    
    return index > oindex ? 1 : -1;
  }

  /**
   * @see de.willuhn.jameica.gui.boxes.Box#isActive()
   */
  public boolean isActive()
  {
    return true;
  }
}


/*********************************************************************
 * $Log: AbstractBox.java,v $
 * Revision 1.1  2006/06/29 23:10:01  willuhn
 * @N Box-System aus Hibiscus in Jameica-Source verschoben
 *
 * Revision 1.2  2005/11/20 23:39:11  willuhn
 * @N box handling
 *
 * Revision 1.1  2005/11/09 01:13:53  willuhn
 * @N chipcard modul fuer AMD64 vergessen
 * @N Startseite jetzt frei konfigurierbar
 *
 **********************************************************************/