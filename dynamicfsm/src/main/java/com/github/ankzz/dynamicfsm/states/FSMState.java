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
import com.github.ankzz.dynamicfsm.common.CustomXMLReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Implements a State for FSM
 *
 * @version 0.01
 * @author ANKIT
 */
public class FSMState implements java.io.Serializable {
    private static final long serialVersionUID = -7020866901240150728L;
    
    private final String  _curState;
    private HashMap _transitionMap;
    private HashMap _transitions;
    private String  _configFileName;
    private CustomXMLReader _reader;

    private FSMStateAction stateEntry;
    private FSMStateAction stateExit;
    
    /**
     * This Constructor allows to create a FSM with the initial state 
     * specified
     * 
     * 
     * @param state Specifies initial state of FSM
     * @param configFileName Specifies the XML Configuration filename
     * 
     * @throws ParserConfigurationException
     * In case an error is encountered parsing XML Configuration file
     * 
     * @throws SAXException
     * In case an error is encountered in XML format
     * 
     * @throws IOException
     * In case an error is encountered in interacting with configuration file
     */
    public FSMState(String state, String configFileName) 
            throws ParserConfigurationException, SAXException, IOException {
        this._curState = state;
        this._configFileName = configFileName;
        this._reader = new CustomXMLReader(
                this.getClass().getClassLoader().getResourceAsStream(
                this._configFileName)
                );
    }

    /**
     * This Constructor allows to create an FSM with initial state set
     * to specified value "state" and sets the transitions as per the 
     * transition map passed onto to it.
     * 
     * @param state Initial state of the FSM
     * @param map Transition Map of states.
     */
    public FSMState(String state, HashMap map){
        this._curState = state;
        this._transitionMap = map;
        updateNewTransitionMap();
    }
    
    /**
     * Method to allow addition of Messages along with their own
     * corresponding Action
     * 
     * @param message
     * Message to be associated with action
     * 
     * @param action
     * Action to be associated with specified message
     */
    public void addMessages(String message, Object action) {
        this._transitionMap.put(message, action);
        updateNewTransitionMap();
    }
    
    /**
     * Method implicitly generates the Transition Map from
     * the specified XML Configuration file.
     * 
     */
    public void addMessagesAndActions() {
        File _f = new File( this._configFileName );
        if ( _f.exists()  && !_f.isDirectory() && this._reader != null) {
            this._transitionMap = this._reader.getStateInfo(this._curState);
            updateNewTransitionMap();
        }
    }
    
    /**
     *  Method to update new Transition Map
     * @return
    private void updateNewTransitionMap() {
     */
    private void updateNewTransitionMap() {
        if (_transitionMap!=null) {
            if (_transitions==null) _transitions = new HashMap<>();
            Iterator iter = _transitionMap.entrySet().iterator();
            while(iter.hasNext()) {
                Map.Entry next = (Map.Entry) iter.next();
                String[] val = ((String) next.getValue()).split(":",2);
                this._transitions.put(next.getKey(), 
                        new FSMTransitionInfo(val[0], val[1]));
            }
        }
    }
    
    /**
     * Method to allow addition of Messages along with their own
     * corresponding Action
     * 
     * @param message message for which action is being assigned
     * @param act action method which needs to be assigned
     */
    public void addMessageAction(String message, FSMAction act) {
        if (_transitions!=null) {
            if (_transitions.containsKey(message)) {
                ((FSMTransitionInfo)_transitions.get(message)).
                        updateAction(act);
            }
        }
    }
    
    /**
     * Method to set the Action method used to specify the exit method for
     * this state
     * 
     * @param act Action to be associated pre-transition
     */
    public void setBeforeTransition(FSMStateAction act) {this.stateEntry= act;}
    
    /**
     * Method to set the Action method used to specify the exit method for
     * this state
     * 
     * @param act Action to be associated post-transition
     */
    public void setAfterTransition(FSMStateAction act) {this.stateExit= act;}
    
    /**
     * Method to return the entire Transition Map
     * 
     * @return 
     * Returns complete transition map associated with FSM
     */
    public HashMap getTransitionMap() { return this._transitionMap;}
    
    /**
     * Method to return State-Name of the FSM State
     * 
     * @return 
     * Returns current state of the FSM
     */
    public String getCurrentState() { return this._curState; }
    
    /**
     * Method to return new entire Transition Map
     * 
     * @return 
     * Returns associated transitions
     */
    public Map getNewTransitionMap() { return this._transitions; }

    public FSMStateAction getBeforeTransition() { return stateEntry; }
    
    public FSMStateAction getAfterTransition() { return stateExit; }
}
