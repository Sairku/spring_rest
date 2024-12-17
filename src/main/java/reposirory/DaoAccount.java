package reposirory;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class AccountRepository implements Dao<Account> {
    private final List<Account> accounts = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1); // Генератор ID

    @Override
    public Account save(Account account) {
        if (account.getId() == null) {
            account.setId(idGenerator.getAndIncrement());
        }
        accounts.add(account);
        return account;
    }

    @Override
    public boolean delete(Account account) {
        return accounts.removeIf(a -> a.getId().equals(account.getId()));
    }

    @Override
    public void deleteAll(List<Account> entities) {
        accounts.removeAll(entities);
    }

    @Override
    public void saveAll(List<Account> entities) {
        for (Account account : entities) {
            save(account);
        }
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }

    @Override
    public boolean deleteById(long id) {
        return accounts.removeIf(a -> a.getId().equals(id));
    }

    @Override
    public Account getOne(long id) {
        Optional<Account> account = accounts.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();

        return account.orElseThrow(() -> new IllegalArgumentException("Рахунок із ID " + id + " не знайдено"));
    }

    public Optional<Account> findByNumber(String number) {
        return accounts.stream()
                .filter(a -> a.getNumber().equals(number))
                .findFirst();
    }
}
