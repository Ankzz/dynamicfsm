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
package com.github.ankzz.dynamicfsm.action;

/**
 * <h1>An abstract implementation of Action for a Finite State Machine (FSM)</h1>
 * 
 * <p>This implementation provides a framework of the Action part of the FSM.
 * This Class is an abstract class and needs to be implemented to instantiate.
 * </p>
 * 
 * @version 0.01
 * @author ANKIT
 */
public abstract class FSMAction {
    
    /**
     * 
     * Abstract method; needs to be implemented
     * 
     * @param curState
     * This value represents the Current State of the FSM.
     * @param message
     * This value specifies the Message for the FSM in Current state. 
     * @param args 
     * This value specifies the argument if any to be passed to the State Node.
     * @param nextState 
     * This value specifies the State to be transitioned to; iff, FSM transition
     * happens.
     * @return  
     * Returns true if action was successfully executed, otherwise false
     */
    public abstract boolean action(String curState, String message, String nextState, Object args);
    
    /**
     * Method is called after successful execution of the FSM transition.
     * Not mandatory to be implemented; can be over-ridden
     * 
     * @param curState 
     * This value represents the Current State of the FSM. 
     * @param message 
     * This value specifies the Message for the FSM in Current state. 
     * @param args 
     * This value specifies the argument if any to be passed to the State Node.
     * @param nextState 
     * This value specifies the State to be transitioned to; iff, Fsm transition
     * happens.
     */
    public void afterTransition(String curState, String message, String nextState, Object args) {
        /*
         * Can be used by the calling function after transition has taken place
         */
    }

    /**
     * Method is called before action method is invoked.
     * Not mandatory to be implemented; can be over-ridden
     * 
     * @param curState 
     * This value represents the Current State of the FSM. 
     * @param message 
     * This value specifies the Message for the FSM in Current state. 
     * @param args 
     * This value specifies the argument if any to be passed to the State Node.
     * @param nextState 
     * This value specifies the State to be transitioned to; iff, Fsm transition
     * happens.
     */
    public void entry(String curState, String message, String nextState, Object args) {
        /*
         * Can be used by the calling function after transition has taken place
         */
    }

    /**
     * Method is called after action method is invoked. This method is invoked
     * irrespective of transition status.
     * Not mandatory to be implemented; can be over-ridden
     * 
     * @param curState 
     * This value represents the Current State of the FSM. 
     * @param message 
     * This value specifies the Message for the FSM in Current state. 
     * @param args 
     * This value specifies the argument if any to be passed to the State Node.
     * @param nextState 
     * This value specifies the State to be transitioned to; iff, Fsm transition
     * happens.
     */
    public void exit(String curState, String message, String nextState, Object args) {
        /*
         * Can be used by the calling function after transition has taken place
         */
    }
    
}
