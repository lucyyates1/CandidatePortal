package tech.geek.CandidatePortal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.geek.CandidatePortal.entity.Client;
import tech.geek.CandidatePortal.repo.ClientRepo;

@Service
public class ClientService {
    @Autowired
    private ClientRepo repository;

    public Client saveClient(Client client){
        return repository.save(client);
    }

    public List<Client> saveClients(List<Client> clients){
        return repository.saveAll(clients);
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client getClientById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteClient(long id) {
        repository.deleteById(id);
    }
}
