package com.pettheanimals;

import org.junit.Test;

import static org.junit.Assert.*;

public class PetResponsesTest
{
    @Test
    public void testCustomLineOverridesDefault()
    {
        String custom = "Custom line";
        String result = PetResponses.buildLine("dog", custom);
        assertEquals("Custom line", result);
    }
}

