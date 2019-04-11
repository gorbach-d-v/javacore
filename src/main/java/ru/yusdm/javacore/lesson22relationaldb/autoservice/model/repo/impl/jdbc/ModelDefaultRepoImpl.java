package ru.yusdm.javacore.lesson22relationaldb.autoservice.model.repo.impl.jdbc;

import ru.yusdm.javacore.lesson22relationaldb.autoservice.common.business.database.datasource.HikariCpDataSource;
import ru.yusdm.javacore.lesson22relationaldb.autoservice.common.business.exception.SqlError;
import ru.yusdm.javacore.lesson22relationaldb.autoservice.common.solutions.repo.jdbc.QueryWrapper;
import ru.yusdm.javacore.lesson22relationaldb.autoservice.model.domain.Model;
import ru.yusdm.javacore.lesson22relationaldb.autoservice.model.domain.ModelDiscriminator;
import ru.yusdm.javacore.lesson22relationaldb.autoservice.model.domain.PassengerModel;
import ru.yusdm.javacore.lesson22relationaldb.autoservice.model.domain.TruckModel;
import ru.yusdm.javacore.lesson22relationaldb.autoservice.model.exception.unchecked.UnknownModelDiscriminatorException;
import ru.yusdm.javacore.lesson22relationaldb.autoservice.model.repo.ModelRepo;
import ru.yusdm.javacore.lesson22relationaldb.autoservice.model.search.ModelSearchCondition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.yusdm.javacore.lesson22relationaldb.autoservice.model.domain.ModelDiscriminator.PASSENGER;
import static ru.yusdm.javacore.lesson22relationaldb.autoservice.model.repo.impl.jdbc.ModelMapper.mapPassenger;
import static ru.yusdm.javacore.lesson22relationaldb.autoservice.model.repo.impl.jdbc.ModelMapper.mapTruck;

public class ModelDefaultRepoImpl implements ModelRepo {

    @Override
    public List<? extends Model> search(ModelSearchCondition searchCondition) {
        return null;
    }

    @Override
    public Model insert(Model entity) {
        return null;
    }

    @Override
    public void insert(Collection<Model> items) {
    }

    @Override
    public void update(Model model) {
        try {
            String sql = "UPDATE MODEL " +
                    "SET " +
                    "MARK_ID = ?," +
                    "NAME = ? ," +
                    "DESCRIPTION = ?," +
                    "PRODUCTION_YEAR_START = ?," +
                    "PRODUCTION_YEAR_END = ?   ," +
                    "DISCRIMINATOR = ? ," +
                    "NUMBER_OF_AIR_BAGS = ? ," +
                    "NUMBER_OF_SEATS = ? ," +
                    "AUDIO_SYSTEM_NAME = ?," +
                    "WEIGHT = ?     ," +
                    "EMBEDDED_KITCHEN = ?," +
                    "TANK_SIZE = ?    " +

                    " WHERE ID = ? ";

            QueryWrapper.executeUpdate(sql, ps -> {
                AtomicInteger index = new AtomicInteger(0);
                appendPreparedStatementParamsForModel(ps, model, index);

                switch (model.getDiscriminator()) {

                    case PASSENGER: {
                        appendPreparedStatementParamsForPassengerModel(ps, (PassengerModel) model, index);
                        break;
                    }
                    case TRUCK: {
                        appendPreparedStatementParamsForTruckModel(ps, (TruckModel) model, index);
                        break;
                    }
                }
                return ps;
            });
        } catch (SQLException e) {
            throw new SqlError(e);
        }
    }

    private void appendPreparedStatementParamsForModel(PreparedStatement ps, Model model, AtomicInteger index) throws SQLException {
        ps.setLong(index.incrementAndGet(), model.getMarkId());
        ps.setString(index.incrementAndGet(), model.getName());
        ps.setString(index.incrementAndGet(), model.getDescription());
        ps.setInt(index.incrementAndGet(), model.getProductionYearStart());
        ps.setInt(index.incrementAndGet(), model.getProductionYearEnd());
        ModelDiscriminator discriminator = model.getDiscriminator();
        ps.setString(index.incrementAndGet(), discriminator.toString());
    }

    private void appendPreparedStatementParamsForPassengerModel(PreparedStatement ps, PassengerModel passenger, AtomicInteger index) throws SQLException {
        ps.setInt(index.incrementAndGet(), passenger.getNumberOfAirbags());
        ps.setInt(index.incrementAndGet(), passenger.getNumberOfSeats());
        ps.setString(index.incrementAndGet(), passenger.getAudioSystemName());
        ps.setNull(index.incrementAndGet(), Types.INTEGER);
        ps.setNull(index.incrementAndGet(), Types.BOOLEAN);
        ps.setNull(index.incrementAndGet(), Types.INTEGER);
    }

    private void appendPreparedStatementParamsForTruckModel(PreparedStatement ps, TruckModel truck, AtomicInteger index) throws SQLException {
        ps.setNull(index.incrementAndGet(), Types.INTEGER);
        ps.setNull(index.incrementAndGet(), Types.INTEGER);
        ps.setNull(index.incrementAndGet(), Types.INTEGER);
        ps.setInt(index.incrementAndGet(), truck.getWeight());
        ps.setBoolean(index.incrementAndGet(), truck.isEmbeddedKitchen());
        ps.setInt(index.incrementAndGet(), truck.getTankSize());
    }

    @Override
    public Optional<Model> findById(Long id) {
        try {
            return QueryWrapper.selectOne("SELECT * FROM MODEL WHERE ID = ?",

                    (rs) -> {
                        String discriminatorStr = rs.getString("");
                        return ModelDiscriminator.getDiscriminatorByName(discriminatorStr)
                                .map(discriminator -> PASSENGER.equals(discriminator) ? mapPassenger(rs) : mapTruck(rs))
                                .orElseThrow(UnknownModelDiscriminatorException::new);
                    },

                    ps -> {
                        ps.setLong(1, id);
                        return ps;
                    });
        } catch (SQLException e) {
            throw new SqlError(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            QueryWrapper.executeUpdate("DELETE FROM MODEL WHERE ID = ?", ps -> {
                ps.setLong(1, id);
                return ps;
            });
        } catch (SQLException e) {
            throw new SqlError(e);
        }
    }

    @Override
    public void printAll() {
        findAll().forEach(System.out::println);
    }

    @Override
    public List<Model> findAll() {
        try {
            return QueryWrapper.select("SELECT * FROM MODEL",
                    (rs) -> {
                        String discriminatorStr = rs.getString("DISCRIMINATOR");
                        return ModelDiscriminator.getDiscriminatorByName(discriminatorStr)
                                .map(discriminator -> PASSENGER.equals(discriminator) ? mapPassenger(rs) : mapTruck(rs))
                                .orElseThrow(UnknownModelDiscriminatorException::new);
                    }
            );
        } catch (SQLException e) {
            throw new SqlError(e);
        }
    }

    @Override
    public int countAll() {
        try {
            return QueryWrapper.selectOne("SELECT COUNT(*) AS CNT FROM MODEL",
                    (rs) -> rs.getInt("CNT")).orElse(0);
        } catch (SQLException e) {
            throw new SqlError(e);
        }
    }

    private Connection getConnection() {
        return HikariCpDataSource.getInstance().getConnection();
    }
}