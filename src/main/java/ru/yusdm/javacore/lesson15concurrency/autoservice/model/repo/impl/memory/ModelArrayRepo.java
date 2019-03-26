package ru.yusdm.javacore.lesson15concurrency.autoservice.model.repo.impl.memory;

import ru.yusdm.javacore.lesson15concurrency.autoservice.common.business.search.Paginator;
import ru.yusdm.javacore.lesson15concurrency.autoservice.common.solutions.utils.ArrayUtils;
import ru.yusdm.javacore.lesson15concurrency.autoservice.common.solutions.utils.CollectionUtils;
import ru.yusdm.javacore.lesson15concurrency.autoservice.model.domain.Model;
import ru.yusdm.javacore.lesson15concurrency.autoservice.model.domain.ModelDiscriminator;
import ru.yusdm.javacore.lesson15concurrency.autoservice.model.domain.PassengerModel;
import ru.yusdm.javacore.lesson15concurrency.autoservice.model.domain.TruckModel;
import ru.yusdm.javacore.lesson15concurrency.autoservice.model.repo.ModelRepo;
import ru.yusdm.javacore.lesson15concurrency.autoservice.model.search.ModelSearchCondition;
import ru.yusdm.javacore.lesson15concurrency.autoservice.model.search.PassengerModelSearchCondition;
import ru.yusdm.javacore.lesson15concurrency.autoservice.model.search.TruckModelSearchCondition;
import ru.yusdm.javacore.lesson15concurrency.autoservice.storage.SequenceGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.yusdm.javacore.lesson15concurrency.autoservice.storage.Storage.modelsArray;
import static ru.yusdm.javacore.lesson15concurrency.autoservice.storage.Storage.modelsList;


public class ModelArrayRepo implements ModelRepo {

    private int modelIndex = -1;

    @Override
    public void insert(Model model) {
        if (modelIndex == modelsArray.length - 1) {
            Model[] newArrModels = new Model[modelsArray.length * 2];
            System.arraycopy(modelsArray, 0, newArrModels, 0, modelsArray.length);
            modelsArray = newArrModels;
        }

        modelIndex++;
        model.setId(SequenceGenerator.getNextValue());
        modelsArray[modelIndex] = model;
    }

    @Override
    public Model findById(Long id) {
        Integer modelIndex = findModelIndexById(id);
        if (modelIndex != null) {
            return modelsArray[modelIndex];
        }

        return null;
    }

    @Override
    public void update(Model model) {
        //we already in memory, no need to update object
    }

    @Override
    public List<? extends Model> search(ModelSearchCondition searchCondition) {
        ModelDiscriminator modelDiscriminator = searchCondition.getModelDiscriminator();

        List<? extends Model> result = modelsList;

        switch (modelDiscriminator) {
            case PASSENGER: {
                result = searchPassengerModels((PassengerModelSearchCondition) searchCondition);
                break;
            }
            case TRUCK: {
                result = searchTruckModels((TruckModelSearchCondition) searchCondition);
                break;
            }
        }

        if (!result.isEmpty() && searchCondition.shouldPaginate()) {
            result = getPageableData(result, searchCondition.getPaginator());
        }

        return result;
    }

    private List<? extends Model> getPageableData(List<? extends Model> models, Paginator paginator) {
        return CollectionUtils.getPageableData(models, paginator.getLimit(), paginator.getOffset());
    }


    private List<TruckModel> searchTruckModels(TruckModelSearchCondition searchCondition) {
        TruckModel[] foundModels = new TruckModel[modelsArray.length];
        int resultIndex = 0;

        for (Model model : modelsList) {

            if (ModelDiscriminator.TRUCK.equals(model.getDiscriminator())) {
                TruckModel truckModel = (TruckModel) model;

                boolean found = true;
                if (searchCondition.searchByTankSize()) {
                    found = searchCondition.getTankSize().equals(truckModel.getTankSize());
                }

                if (found && searchCondition.searchByEmbeddedKitchen()) {
                    found = searchCondition.getEmbeddedKitchen().equals(truckModel.isEmbeddedKitchen());
                }

                if (found && searchCondition.searchByWeight()) {
                    found = searchCondition.getWeight().equals(truckModel.getWeight());
                }

                if (found) {
                    foundModels[resultIndex] = truckModel;
                    resultIndex++;
                }
            }

        }

        if (resultIndex > 0) {
            TruckModel toReturn[] = new TruckModel[resultIndex];
            System.arraycopy(foundModels, 0, toReturn, 0, resultIndex);
            return new ArrayList<>(Arrays.asList(toReturn));
        }

        return Collections.emptyList();
    }


    private List<PassengerModel> searchPassengerModels(PassengerModelSearchCondition searchCondition) {

        PassengerModel[] foundModels = new PassengerModel[modelsArray.length];
        int resultIndex = 0;

        for (Model model : modelsList) {

            if (ModelDiscriminator.PASSENGER.equals(model.getDiscriminator())) {
                PassengerModel passengerModel = (PassengerModel) model;

                boolean found = true;
                if (searchCondition.searchByAudioSystemName()) {
                    found = searchCondition.getAudioSystemName().equals(passengerModel.getAudioSystemName());
                }

                if (found && searchCondition.searchByNumberOfSeats()) {
                    found = searchCondition.getNumberOfSeats().equals(passengerModel.getNumberOfSeats());
                }

                if (found && searchCondition.searchByNumberOfAirBags()) {
                    found = searchCondition.getNumberOfAirbags().equals(passengerModel.getNumberOfAirbags());
                }


                if (found) {
                    foundModels[resultIndex] = passengerModel;
                    resultIndex++;
                }
            }

        }

        if (resultIndex > 0) {
            PassengerModel[] toReturn = new PassengerModel[resultIndex];
            System.arraycopy(foundModels, 0, toReturn, 0, resultIndex);
            return new ArrayList<>(Arrays.asList(toReturn));
        }

        return Collections.emptyList();
    }


    @Override
    public void deleteById(Long id) {
        Integer modelIndex = findModelIndexById(id);

        if (modelIndex != null) {
            deleteModelByIndex(modelIndex);
        }
    }

    private void deleteModelByIndex(int index) {
        ArrayUtils.removeElement(modelsArray, index);
        modelIndex--;
    }

    @Override
    public void printAll() {
        for (Model model : modelsArray) {
            if (model != null) {
                System.out.println(model);
            }
        }
    }

    private Integer findModelIndexById(long modelId) {
        for (int i = 0; i < modelsArray.length; i++) {
            if (modelsArray[i] != null && Long.valueOf(modelId).equals(modelsArray[i].getId())) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Model> findAll() {
        return new ArrayList<>(Arrays.asList(modelsArray));
    }

    @Override
    public int countAll() {
        return modelsArray.length;
    }
}
