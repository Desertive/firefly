package com.desertive.firefly.core.services.actions;

import com.desertive.firefly.core.data.builders.SectionBuilder;
import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.greghaskins.spectrum.dsl.specification.Specification.beforeEach;
import static com.greghaskins.spectrum.dsl.specification.Specification.describe;
import static com.greghaskins.spectrum.dsl.specification.Specification.it;

@RunWith(Spectrum.class)
@SpringBootTest
@ActiveProfiles("test")
public class RandomBlinkActionServiceTest {

    ActionServiceFactory actionServiceFactory = new ActionServiceFactory();
    ActionService actionService;

    /* * * * * * * * * *
     *      Tests      *
     * * * * * * * * * */

    {
        describe("Random blink action", () -> {

            beforeEach(() -> {
                actionService = actionServiceFactory.getInstance(ActionType.RANDOM_BLINK);
            });

            it("should return four transition steps", () -> {

            });

        });
    }

    private ActionRequest.Section buildSection() {
        return new SectionBuilder()
            .setStart(1)
            .setEnd(3)
            .setType(ActionType.RANDOM_BLINK)
            .addColor(2, 2, 2)
            .addProperty("transition", 2)
            .addProperty("r", 1)
            .addProperty("g", 1)
            .addProperty("b", 1)
            .build();
    }
}
