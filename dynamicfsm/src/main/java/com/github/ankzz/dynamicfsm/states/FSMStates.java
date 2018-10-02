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
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Implements the list of FSM States
 * 
 * <p>
 * This implementation caters to the need of maintaining the state of
 * of a FSM.
 * </p>
 * 
 * @version 0.01
 * @author ANKIT
 */
public class FSMStates implements java.io.Serializable {
    private static final long serialVersionUID = -7575735494729831944L;
    
    private ArrayList _fsmStates;
    private ArrayList _states;
    private FSMState _curState;
    private String _configFileName="config.xml";
    
    /**
     * <p>
     * This constructor allows to create a FSM from a Configuration File
     * This constructor allows a developer to have flexibility of specifying
     * an external XML Configuration file with a definite path.
     * </p>
     * 
     * 
     * @param configFName
     * Configuration file path.
     * 
     * @param extFile 
     * Flag to indicate if resource file needs to be used or an external 
     * configuration file needs to be used.
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
    public FSMStates(String configFName, boolean extFile) 
            throws ParserConfigurationException, SAXException, IOException {
        
        if(!"".equals(configFName)) this._configFileName = configFName;
        this._fsmStates = new ArrayList<>();
        CustomXMLReader _r;
        if(!extFile) {
            _r = new CustomXMLReader(
                this.getClass().getClassLoader().getResourceAsStream(
                this._configFileName)
                );
        } else {
            _r = new CustomXMLReader( this._configFileName );
        }
        
        _states = _r.getStates();
        
        _states.forEach((_state) -> {
            HashMap _t = _r.getStateInfo((String)_state);
            this._fsmStates.add(new FSMState ((String) _state, _t));
        });
        
        this._curState = (FSMState) this._fsmStates.get(0);
    }

    /**
     * <p>
     * This constructor allows to create a FSM from a InputStream
     * This InputStream can be a :</p>
     * <ol>
     *  <li> InputStream of a XML configuration file.</li>
     * </ol>
     * 
     * <p>
     * This constructor allows a developer to have flexibility of specifying
     * InputStream of a resource defined within a project.</p>
     * 
     * 
     * @param configFStream InputStream of a XML Configuration file
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
    public FSMStates(InputStream configFStream) 
            throws ParserConfigurationException, SAXException, IOException {
        CustomXMLReader _r = new CustomXMLReader(configFStream);
        this._fsmStates = new ArrayList<>();
        _states = _r.getStates();
        
        _states.forEach((_state) -> {
            HashMap _t = _r.getStateInfo((String)_state);
            this._fsmStates.add(new FSMState ((String) _state, _t));
        });
        
        this._curState = (FSMState) this._fsmStates.get(0);
    }
    
    /**
     * This constructor uses the default configuration file.
     * 
     * This Constructor shall not be used and still exists to just test
     * or understand the behavior.
     * 
     * @throws ParserConfigurationException
     * In case an error is encountered parsing XML Configuration file
     * 
     * @throws SAXException
     * In case an error is encountered in XML format
     * 
     * @throws IOException
     * In case an error is encountered in interacting with configuration file
     * @deprecated 
     */
    @Deprecated
    public FSMStates() 
            throws ParserConfigurationException, SAXException, IOException {
        this("", false);
    }

    /**
     * This method allows to set the current state of the FSM
     * 
     * 
     * @param f State to be set as current state for the FSM
     */
    public void setCurrentState(FSMState f) { this._curState = f; }
    
    /**
     * This method allows to set specific action methods for a specific
     * message/action. 
     * If specified, this method shall be called when specified message is
     * received in specified list of states.
     * 
     *
     * @param states List of states for which specified action method needs to
     *               be initiated
     * @param message Message/Action which is received 
     * @param act Action method which needs to be initiated when message/action
     *            is received
     */
    public void setAction(ArrayList<String> states, String message, 
            FSMAction act) {
        int count = states.size();
        for (Iterator it = this._fsmStates.iterator(); it.hasNext();) {
            FSMState i = (FSMState) it.next();
            if (states.contains(i.getCurrentState())) {
                i.addMessageAction(message, act);
                count--;
                if (count<=0) break;
            }
        }
    }
    
    /**
     * This method allows to set specific action methods for a specific
     * message/action. 
     * If specified, this method shall be called when specified message is
     * received in specified state.
     * 
     *
     * @param state State for which this override action method needs to be 
     *              initiated
     * @param message Message/Action which is received 
     * @param act Action method which needs to be initiated when message/action
     *            is received
     */
    public void setAction(String state, String message, 
            FSMAction act) {
        setAction(new ArrayList(Arrays.asList(state)), message, act);
    }
    
    /**
     * This method allows to set specific action methods for a specific
     * message/action. 
     * If specified, this method shall be called when specified message is
     * received in any state.
     * 
     * 
     * @param message Message/Action which is received 
     * @param act Action method which needs to be initiated when message/action
     *            is received
     */
    public void setAction(String message, FSMAction act) {
        ArrayList states = (ArrayList) getAllStates();
        int count = states.size();
        for (Iterator it = this._fsmStates.iterator(); it.hasNext();) {
            FSMState i = (FSMState) it.next();
            if (states.contains(i)) {
                i.addMessageAction(message, act);
                count--;
                if (count<=0) break;
            }
        }    
    }
    
    /**
     * This method allows to set entry methods for a specific
     * State. 
     * If specified, this method shall be invoked before action method is 
     * invoked when any message is received in specified state.
     * 
     * 
     * @param state State for which entry function is being assigned
     * @param act Entry method which needs to be initiated when any message/action
     *            is received on specified state
     */
    public void setStateBeforeTransition(String state, FSMStateAction act) {
        for (Iterator it = this._fsmStates.iterator(); it.hasNext();) {
            FSMState i = (FSMState) it.next();
            if (i.getCurrentState().equals(state)) {
                i.setBeforeTransition(act);
                break;
            }
        }
    }

    /**
     * This method allows to set entry methods for a specific
     * State. 
     * If specified, this method shall be invoked before action method is 
     * invoked when any message is received in specified state.
     * 
     * 
     * @param states List of State for which entry function is being assigned
     *               If passed null, entry method is applied to all states
     * @param act Entry method which needs to be initiated when any message/action
     *            is received on specified state
     */
    public void setStateBeforeTransition(ArrayList<String> states, 
            FSMStateAction act) {
        if (states==null) { states = _states;}
        
        for (Iterator it = this._fsmStates.iterator(); it.hasNext();) {
            FSMState i = (FSMState) it.next();
            if (states.contains(i.getCurrentState())) {
                i.setBeforeTransition(act);
            }
        }
    }

    /**
     * This method allows to set entry methods for a specific
     * State. 
     * If specified, this method shall be invoked after action method has been 
     * invoked when any message is received in specified state.
     * 
     * 
     * @param state State for which exit function is being assigned
     * @param act Exit method which needs to be initiated when any message/action
     *            is received on specified state
     */
    public void setStateAfterTransition(String state, FSMStateAction act) {
        for (Iterator it = this._fsmStates.iterator(); it.hasNext();) {
            FSMState i = (FSMState) it.next();
            if (i.getCurrentState().equals(state)) {
                i.setAfterTransition(act);
                break;
            }
        }    
    }

    /**
     * This method allows to set entry methods for a specific
     * State. 
     * If specified, this method shall be invoked after action method has been 
     * invoked when any message is received in specified state.
     * 
     * 
     * @param states List of State for which entry function is being assigned
     *               If passed null, entry method is applied to all states
     * @param act Exit method which needs to be initiated when any message/action
     *            is received on specified state
     */
    public void setStateAfterTransition(ArrayList<String> states, 
            FSMStateAction act) {
        if (states==null) {states = _states;}
        
        for (Iterator it = this._fsmStates.iterator(); it.hasNext();) {
            FSMState i = (FSMState) it.next();
            if (states.contains(i.getCurrentState())) {
                i.setAfterTransition(act);
            }
        }
    }

    /**
     * This method allows to get the current state of the FSM
     * 
     * 
     * @return Returns a FSMState object
     */
    public FSMState getCurrentState() { return this._curState;}
    
    /**
     * This method returns the list a FSM States configured for this FSM.
     * 
     * @return Returns the list of FSM states
     */
    public List getAllStates() { return this._fsmStates;}
}
