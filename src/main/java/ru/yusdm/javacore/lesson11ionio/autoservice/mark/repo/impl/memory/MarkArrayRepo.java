package ru.yusdm.javacore.lesson11ionio.autoservice.mark.repo.impl.memory;

import ru.yusdm.javacore.lesson11ionio.autoservice.common.solutions.utils.ArrayUtils;
import ru.yusdm.javacore.lesson11ionio.autoservice.mark.domain.Mark;
import ru.yusdm.javacore.lesson11ionio.autoservice.mark.repo.MarkRepo;
import ru.yusdm.javacore.lesson11ionio.autoservice.mark.search.MarkSearchCondition;
import ru.yusdm.javacore.lesson11ionio.autoservice.storage.SequenceGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.yusdm.javacore.lesson11ionio.autoservice.storage.Storage.marksArray;


public class MarkArrayRepo implements MarkRepo {
    private MarkOrderingComponent orderingComponent = new MarkOrderingComponent();
    private int markIndex = -1;

    @Override
    public void insert(Mark mark) {
        if (markIndex == marksArray.length - 1) {
            Mark[] newArrMarks = new Mark[marksArray.length * 2];
            System.arraycopy(marksArray, 0, newArrMarks, 0, marksArray.length);
            marksArray = newArrMarks;
        }

        markIndex++;
        mark.setId(SequenceGenerator.getNextValue());
        marksArray[markIndex] = mark;
    }

    @Override
    public void update(Mark mark) {
        //we already in memory, no need to update object
    }

    @Override
    public Mark findById(Long id) {
        Integer markIndex = findMarkIndexById(id);
        if (markIndex != null) {
            return marksArray[markIndex];
        }

        return null;
    }

    @Override
    public List<Mark> search(MarkSearchCondition searchCondition) {
        if (searchCondition.getId() != null) {
            return Collections.singletonList(findById(searchCondition.getId()));
        } else {
            List<Mark> result = doSearch(searchCondition);
            boolean needOrdering = !result.isEmpty() && searchCondition.needOrdering();

            if (needOrdering) {
                orderingComponent.applyOrdering(result, searchCondition);
            }

            return result;
        }
    }

    private List<Mark> doSearch(MarkSearchCondition searchCondition) {
        Mark[] result = new Mark[marksArray.length];
        int resultIndex = 0;

        for (Mark mark : marksArray) {
            if (mark != null) {
                boolean found = true;

                if (searchCondition.searchByCountry()) {
                    found = searchCondition.getCountry().equals(mark.getCountry());
                }

                if (found && searchCondition.searchByName()) {
                    found = searchCondition.getName().equals(mark.getName());
                }

                if (found) {
                    result[resultIndex] = mark;
                    resultIndex++;
                }
            }
        }

        if (resultIndex > 0) {
            Mark toReturn[] = new Mark[resultIndex];
            System.arraycopy(result, 0, toReturn, 0, resultIndex);
            return new ArrayList<>(Arrays.asList(toReturn));
        }

        return Collections.emptyList();
    }

    @Override
    public void deleteById(Long id) {
        Integer markIndex = findMarkIndexById(id);

        if (markIndex != null) {
            deleteMarkByIndex(markIndex);
        }
    }

    private void deleteMarkByIndex(int index) {
        ArrayUtils.removeElement(marksArray, index);
        markIndex--;
    }

    @Override
    public void printAll() {
        for (Mark mark : marksArray)
            if (mark != null) {
                System.out.println(mark);
            }
    }

    private Integer findMarkIndexById(long markId) {
        for (int i = 0; i < marksArray.length; i++) {
            if (marksArray[i] != null && Long.valueOf(markId).equals(marksArray[i].getId())) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Mark> findAll() {
        return new ArrayList<>(Arrays.asList(marksArray));
    }
}
