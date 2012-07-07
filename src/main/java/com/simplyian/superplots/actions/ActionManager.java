package com.simplyian.superplots.actions;

import java.util.HashMap;
import java.util.Map;

import com.simplyian.superplots.SuperPlotsPlugin;

/**
 * Manages actions.
 * 
 * @author simplyianm
 */
public class ActionManager {
    private Map<String, BaseAction> actions = new HashMap<String, BaseAction>();

    private SuperPlotsPlugin main;

    public ActionManager(SuperPlotsPlugin main) {
        this.main = main;
        registerDefaultActions();
    }

    private void registerDefaultActions() {
        addAction(new ActionCoown(main));
        addAction(new ActionCreate(main));
        addAction(new ActionDeposit(main));
        addAction(new ActionDisband(main));
        addAction(new ActionExpand(main));
        addAction(new ActionFriend(main));
        addAction(new ActionInfo(main));
        addAction(new ActionKick(main));
        addAction(new ActionPrivate(main));
        addAction(new ActionProtect(main));
        addAction(new ActionPublic(main));
        addAction(new ActionRename(main));
        addAction(new ActionShrink(main));
        addAction(new ActionUnprotect(main));
        addAction(new ActionWithdraw(main));
    }

    /**
     * Adds an action to the Action manager.
     * 
     * @param action
     */
    public void addAction(BaseAction action) {
        actions.put(action.getName().toLowerCase(), action);
    }

    /**
     * Gets an action with the given name.
     * 
     * @param name
     * @return
     */
    public BaseAction getAction(String name) {
        return actions.get(name.toLowerCase());
    }
}
