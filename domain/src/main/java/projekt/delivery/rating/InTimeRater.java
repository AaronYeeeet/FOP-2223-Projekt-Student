package projekt.delivery.rating;

import projekt.delivery.event.DeliverOrderEvent;
import projekt.delivery.event.Event;
import projekt.delivery.event.OrderReceivedEvent;
import projekt.delivery.routing.ConfirmedOrder;
import projekt.delivery.simulation.Simulation;

import java.util.List;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Rates the observed {@link Simulation} based on the punctuality of the orders.<p>
 *
 * To create a new {@link InTimeRater} use {@code InTimeRater.Factory.builder()...build();}.
 */
public class InTimeRater implements Rater {

    public static final RatingCriteria RATING_CRITERIA = RatingCriteria.IN_TIME;

    private final long ignoredTicksOff;
    private final long maxTicksOff;
    private long totalMaxTicksOff;

    private long actualTotalTicksOff;

    private int orderAmount;

    /**
     * Creates a new {@link InTimeRater} instance.
     * @param ignoredTicksOff The amount of ticks this {@link InTimeRater} ignores when dealing with an {@link ConfirmedOrder} that didn't get delivered in time.
     * @param maxTicksOff The maximum amount of ticks too late/early this {@link InTimeRater} considers.
     */
    private InTimeRater(long ignoredTicksOff, long maxTicksOff) {
        if (ignoredTicksOff < 0) throw new IllegalArgumentException(String.valueOf(ignoredTicksOff));
        if (maxTicksOff <= 0) throw new IllegalArgumentException(String.valueOf(maxTicksOff));

        this.ignoredTicksOff = ignoredTicksOff;
        this.maxTicksOff = maxTicksOff;
    }

    @Override
    public double getScore() {
        double r;
        if (maxTicksOff == 0 ||totalMaxTicksOff == 0)
            r =  0;
        else r = 1 - (double) actualTotalTicksOff / (double) totalMaxTicksOff;
        return r;
    }

    @Override
    public void onTick(List<Event> events, long tick) {
        for (Event event : events) {
            if (event instanceof OrderReceivedEvent) {
                totalMaxTicksOff += maxTicksOff;
                actualTotalTicksOff += maxTicksOff;
            }
            if (event instanceof DeliverOrderEvent orderEvent) {
                ConfirmedOrder order = orderEvent.getOrder();

                long intervalStart = order.getDeliveryInterval().start();
                long intervalEnd = order.getDeliveryInterval().end();
                long actual = order.getActualDeliveryTick();
                //if (order.getActualDeliveryTick() != null)
                long tmp = 0;
                if (actual < intervalStart - ignoredTicksOff){
                    tmp = intervalStart - ignoredTicksOff - actual;
                    if (tmp > maxTicksOff)
                        tmp = maxTicksOff;
                } else if (actual > intervalEnd + ignoredTicksOff){
                    tmp = actual - (intervalEnd + ignoredTicksOff);
                    if (tmp > maxTicksOff)
                        tmp = maxTicksOff;
                }
                actualTotalTicksOff -= (maxTicksOff - tmp);
            } /*else if (event instanceof OrderReceivedEvent){
                i++;
                totalMaxTicksOff += maxTicksOff;
                actualTotalTicksOff += maxTicksOff;
            }*/
        }
    }

    /**
     * A {@link Rater.Factory} for creating a new {@link InTimeRater}.
     */
    @Override
    public RatingCriteria getRatingCriteria() {
        return RATING_CRITERIA;
    }

    public static class Factory implements Rater.Factory {

        public final long ignoredTicksOff;
        public final long maxTicksOff;

        private Factory(long ignoredTicksOff, long maxTicksOff) {
            this.ignoredTicksOff = ignoredTicksOff;
            this.maxTicksOff = maxTicksOff;
        }

        @Override
        public InTimeRater create() {
            return new InTimeRater(ignoredTicksOff, maxTicksOff);
        }

        /**
         * Creates a new {@link InTimeRater.FactoryBuilder}.
         * @return The created {@link InTimeRater.FactoryBuilder}.
         */
        public static FactoryBuilder builder() {
            return new FactoryBuilder();
        }
    }

    /**
     * A {@link Rater.FactoryBuilder} form constructing a new {@link InTimeRater.Factory}.
     */
    public static class FactoryBuilder implements Rater.FactoryBuilder {

        public long ignoredTicksOff = 5;
        public long maxTicksOff = 25;

        private FactoryBuilder() {}

        public FactoryBuilder setIgnoredTicksOff(long ignoredTicksOff) {
            this.ignoredTicksOff = ignoredTicksOff;
            return this;
        }

        public FactoryBuilder setMaxTicksOff(long maxTicksOff) {
            this.maxTicksOff = maxTicksOff;
            return this;
        }


        @Override
        public Factory build() {
            return new Factory(ignoredTicksOff, maxTicksOff);
        }
    }
}
