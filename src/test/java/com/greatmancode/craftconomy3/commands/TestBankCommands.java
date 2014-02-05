/*
 * This file is part of Craftconomy3.
 *
 * Copyright (c) 2011-2014, Greatman <http://github.com/greatman/>
 *
 * Craftconomy3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Craftconomy3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Craftconomy3.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.greatmancode.craftconomy3.commands;

import com.greatmancode.craftconomy3.Cause;
import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.TestInitializator;
import com.greatmancode.craftconomy3.account.Account;
import com.greatmancode.craftconomy3.commands.bank.BankCreateCommand;
import com.greatmancode.craftconomy3.commands.bank.BankGiveCommand;
import com.greatmancode.craftconomy3.commands.bank.BankTakeCommand;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBankCommands {

    private static final String BANK_ACCOUNT = "testbankaccount39";
    private static final String TEST_USER = "testuser39";
    @Before
    public void setUp() {
        new TestInitializator();
    }

    @Test
    public void testBankCreateCommand() {
        BankCreateCommand command = new BankCreateCommand();
        command.execute(TEST_USER, new String[]{BANK_ACCOUNT});
        assertFalse(Common.getInstance().getAccountManager().exist(Account.BANK_PREFIX + BANK_ACCOUNT));
        Common.getInstance().getAccountManager().getAccount(TEST_USER).set(200, "default", Common.getInstance().getCurrencyManager().getDefaultCurrency().getName(), Cause.USER, "greatman");
        command.execute(TEST_USER, new String[]{BANK_ACCOUNT});
        assertTrue(Common.getInstance().getAccountManager().exist(Account.BANK_PREFIX + BANK_ACCOUNT));
    }

    @Test
    public void testBankGiveCommand() {
        BankGiveCommand command = new BankGiveCommand();
        double initialValue = Common.getInstance().getAccountManager().getAccount(Account.BANK_PREFIX + BANK_ACCOUNT).getBalance("default", Common.getInstance().getCurrencyManager().getDefaultCurrency().getName());
        Common.getInstance().sendConsoleMessage(Level.INFO, initialValue + "");
        command.execute(TEST_USER, new String[]{BANK_ACCOUNT, "wow"});
        assertEquals(initialValue, Common.getInstance().getAccountManager().getAccount(Account.BANK_PREFIX + BANK_ACCOUNT).getBalance("default", Common.getInstance().getCurrencyManager().getDefaultCurrency().getName()), 0);
        command.execute(TEST_USER, new String[]{BANK_ACCOUNT, "100"});
        assertEquals(initialValue + 100, Common.getInstance().getAccountManager().getAccount(Account.BANK_PREFIX + BANK_ACCOUNT).getBalance("default", Common.getInstance().getCurrencyManager().getDefaultCurrency().getName()), 0);
    }

    @Test
    public void testBankTakeCommand() {
        BankTakeCommand command = new BankTakeCommand();
        double initialValue = Common.getInstance().getAccountManager().getAccount(Account.BANK_PREFIX + BANK_ACCOUNT).getBalance("default", Common.getInstance().getCurrencyManager().getDefaultCurrency().getName());
        command.execute(TEST_USER, new String[]{BANK_ACCOUNT, "100"});
        assertEquals(initialValue - 100, Common.getInstance().getAccountManager().getAccount(Account.BANK_PREFIX + BANK_ACCOUNT).getBalance("default", Common.getInstance().getCurrencyManager().getDefaultCurrency().getName()), 0);
    }

    @Test
    public void testBankDeleteCommand() {

    }
}