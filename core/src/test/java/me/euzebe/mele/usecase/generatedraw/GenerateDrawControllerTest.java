package me.euzebe.mele.usecase.generatedraw;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import javaslang.control.Option;
import me.euzebe.mele.spi.DrawsCatalog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GenerateDrawControllerTest {

	@Mock
	DrawsCatalog catalog;

	GenerateDrawController controller;

	@Before
	public void init() {
		controller = new GenerateDrawController(catalog);
	}

	@Test
	public void should_generate_a_draw_then_store_it_when_participants_are_given() {

		Option<Draw> optionalDraw = controller.generateDraw("Niobé", "Ernest");
		assertThat(optionalDraw.isDefined()).isTrue();

		Mockito.verify(catalog, times(1)).add(any(Draw.class));
	}

	@Test
	public void should_generate_an_empty_draw_then_store_it_when_participants_are_given() {
		Option<Draw> optionalDraw = controller.generateDraw();
		assertThat(optionalDraw.isDefined()).isFalse();

		Mockito.verify(catalog, Mockito.never()).add(any(Draw.class));
	}
}
