/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.groundzero.opt;

import de.weltraumschaf.groundzero.opt.commons.CommonsImplementation;
import de.weltraumschaf.groundzero.opt.jcommander.JCommanderImplementation;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;;

/**
 * Tests for {@link OptionsSetup}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class OptionsSetupTest {

    @Test
    public void create_CommonsImplementation() {
        final OptionsSetup opts = OptionsSetup.create(Strategy.COMMONS);
        assertThat(opts, is(not(nullValue())));
        assertThat(opts, is(instanceOf(CommonsImplementation.class)));
    }

    @Test
    public void create_jCommanderImplementation() {
        final OptionsSetup opts = OptionsSetup.create(Strategy.JCOMMANDER);
        assertThat(opts, is(not(nullValue())));
        assertThat(opts, is(instanceOf(JCommanderImplementation.class)));
    }

}