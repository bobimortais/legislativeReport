package org.legislative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.legislative.exception.MissingInputArgumentException;

class LegislativeReportMainTest {

    @Test
    void main_shouldThrowExceptionWhenArgumentListHasMissingParameters() {
        Assertions.assertThrows(MissingInputArgumentException.class, () -> LegislativeReportMain.run(new String[]{"arg1","arg2","arg3"}));
    }
}