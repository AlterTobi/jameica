/**********************************************************************
 *
 * Copyright (c) by Olaf Willuhn
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.gui.internal.action;

import java.net.URL;

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.messaging.QueryMessage;
import de.willuhn.jameica.messaging.StatusBarMessage;
import de.willuhn.jameica.services.RepositoryService;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.system.OperationCanceledException;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Aktion zum Loeschen eines Repository.
 */
public class RepositoryRemove implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {
    if (context == null)
      return;
    
    final I18N i18n = Application.getI18n();

    String s = context.toString();
    
    if (RepositoryService.SYSTEM_REPOSITORY.equalsIgnoreCase(s))
      throw new ApplicationException(i18n.tr("System-Repository darf nicht gel�scht werden"));
    
    URL url = null;
    try
    {
      url = new URL(s);
    }
    catch (Exception e)
    {
      Logger.error("invalid url: " + context,e);
      throw new ApplicationException(i18n.tr("Keine g�ltige Repository-URL angegeben"));
    }
    
    String q = i18n.tr("Sind Sie sicher, da� Sie diese URL l�schen m�chten?\n\n{0}",url.toString());
    
    try
    {
      if (!Application.getCallback().askUser(q))
        return;

      RepositoryService service = Application.getBootLoader().getBootable(RepositoryService.class);
      service.removeRepository(url);
      
      Application.getMessagingFactory().getMessagingQueue("jameica.update.repository.remove").sendMessage(new QueryMessage(url));
      Application.getMessagingFactory().sendMessage(new StatusBarMessage(i18n.tr("Repository-URL gel�scht"),StatusBarMessage.TYPE_SUCCESS));
    }
    catch (OperationCanceledException oce)
    {
      // ignore
    }
    catch (ApplicationException ae)
    {
      throw ae;
    }
    catch (Exception e)
    {
      Logger.error("error while deleting url " + context,e);
      Application.getMessagingFactory().sendMessage(new StatusBarMessage(i18n.tr("Fehler beim L�schen der Repository-URL"),StatusBarMessage.TYPE_ERROR));
    }
  }
}
