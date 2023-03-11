package projekt.runner;

import projekt.delivery.archetype.ProblemArchetype;
import projekt.delivery.archetype.ProblemGroup;
import projekt.delivery.rating.RatingCriteria;
import projekt.delivery.service.DeliveryService;
import projekt.delivery.simulation.BasicDeliverySimulation;
import projekt.delivery.simulation.Simulation;
import projekt.delivery.simulation.SimulationConfig;
import projekt.runner.handler.ResultHandler;
import projekt.runner.handler.SimulationFinishedHandler;
import projekt.runner.handler.SimulationSetupHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.tudalgo.algoutils.student.Student.crash;

public class RunnerImpl implements Runner {

    @Override
    public void run(ProblemGroup problemGroup,
                    SimulationConfig simulationConfig,
                    int simulationRuns,
                    DeliveryService.Factory deliveryServiceFactory,
                    SimulationSetupHandler simulationSetupHandler,
                    SimulationFinishedHandler simulationFinishedHandler,
                    ResultHandler resultHandler) {

        List<Double> inTimeRatings = new ArrayList<>();
        List<Double> amountDeliveredRatings = new ArrayList<>();
        List<Double> travelDistanceRatings = new ArrayList<>();
        Map<ProblemArchetype, Simulation> simulations = createSimulations(problemGroup, simulationConfig, deliveryServiceFactory);
        for (int i=0; i<simulationRuns; i++) {
            for (ProblemArchetype problem : problemGroup.problems()) {
                Simulation simulation = simulations.get(problem);
                simulationSetupHandler.accept(simulation,problem,i);
                simulation.runSimulation(problem.simulationLength());
                if (simulationFinishedHandler.accept(simulation,problem)) return;
                inTimeRatings.add(simulation.getRatingForCriterion(RatingCriteria.IN_TIME));
                amountDeliveredRatings.add(simulation.getRatingForCriterion(RatingCriteria.AMOUNT_DELIVERED));
                travelDistanceRatings.add(simulation.getRatingForCriterion(RatingCriteria.TRAVEL_DISTANCE));
            }
        }
        Map<RatingCriteria,Double> result = Map.of(RatingCriteria.IN_TIME,inTimeRatings.stream().mapToDouble(d -> d).average().orElse(0),
                                                    RatingCriteria.AMOUNT_DELIVERED,amountDeliveredRatings.stream().mapToDouble(d -> d).average().orElse(0),
                                                    RatingCriteria.TRAVEL_DISTANCE,travelDistanceRatings.stream().mapToDouble(d -> d).average().orElse(0));
        resultHandler.accept(result);
    }

    @Override
    public Map<ProblemArchetype, Simulation> createSimulations(ProblemGroup problemGroup,
                                                                SimulationConfig simulationConfig,
                                                                DeliveryService.Factory deliveryServiceFactory) {

        Map<ProblemArchetype, Simulation> map = new HashMap<>();
        for (ProblemArchetype problem : problemGroup.problems()) {
            map.put(problem, new BasicDeliverySimulation(simulationConfig,problem.raterFactoryMap(),deliveryServiceFactory.create(problem.vehicleManager()),problem.orderGeneratorFactory()));
        }
        return map;
    }

}
