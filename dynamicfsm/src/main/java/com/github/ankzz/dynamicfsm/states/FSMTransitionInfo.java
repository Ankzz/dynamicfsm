/**
 *                    GNU LESSER GENERAL PUBLIC LICENSE
 *                          Version 3, 29 June 2007
 * Copyright (C) 2018 Free Software Foundation, Inc. <http://fsf.org>
 * 
 * This file is part of library dynamicfsm.
 * 
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU LESSER GENERAL PUBLIC LICENSE as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. This library can be redistributed
 * or used in case this license is copied as it is.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Author : Ankit
 * Report bugs to : ankzzdev (at) gmail (dot) com
**/
package com.github.ankzz.dynamicfsm.states;

import com.github.ankzz.dynamicfsm.action.FSMAction;

/**
 * File: FSMTransitionInfo.java
 * <p>
 * This implementation implements methods required to store 
 * FSM transition information from one state to another state.
 * </p>
 * 
 * @version 0.01
 * @author ANKIT
 */
public class FSMTransitionInfo implements java.io.Serializable {
    private static final long serialVersionUID = 5836424827126595488L;
    
    private String action;
    private String nextState;
    private transient FSMAction _a;
    
    /**
     *
     * @param message Message / Action for which transitions are being defined
     * @param transState Next state which is attained on successful transition
     * @param a Action function which needs to be executed when specified 
     *          message is received
     */
    public FSMTransitionInfo(String message, String transState, FSMAction a) {
        action = message;
        nextState = transState;
        _a = a;
    }
    
    /**
     *
     * @param message action/message for which transition information is being used
     * @param transState next state which shall be achieved on successful transition
     */
    public FSMTransitionInfo(String message, String transState) {
        this(message, transState, null);
    }
    
    /**
     *
     * @return Returns the name of action/message
     */
    public String getActionName() { return action; }
    
    /**
     *
     * @return Returns the expected next state on successful transition
     */
    public String getNextState() { return nextState; }
    
    /**
     *
     * @param act Action to be associated with a particular transition
     */
    public void updateAction(FSMAction act) { _a = act; }
    
    /**
     *
     * @return
     * Returns default action associated with transition
     */
    public FSMAction getAction() { return _a; }
}
