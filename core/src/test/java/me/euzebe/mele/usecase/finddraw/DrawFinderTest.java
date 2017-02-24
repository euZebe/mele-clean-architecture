package me.euzebe.mele.usecase.finddraw;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import me.euzebe.mele.spi.DrawsCatalog;
import me.euzebe.mele.usecase.generatedraw.Draw;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DrawFinderTest {

    private DrawFinder finder;

    @Mock
    private DrawsCatalog catalog;


    @Before
    public void init() {
        finder = new DrawFinder(catalog);
    }

    @Test
    public void should_return_the_same_thing_as_catalog() {
        assertThat(finder.getAll()).isNull();

		Draw[] draws = new Draw[] { Draw.EMPTY, Draw.EMPTY, Draw.EMPTY };
        when(catalog.getAll()).thenReturn(draws);

        assertThat(finder.getAll()).isEqualTo(draws);
    }



}
