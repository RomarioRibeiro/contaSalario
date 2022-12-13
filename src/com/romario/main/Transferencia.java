package com.romario.main;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.romario.model.Conta;

public class Transferencia {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancosuperPU");
		EntityManager em = emf.createEntityManager();
		Scanner ler = new Scanner(System.in);
		
		Conta conta1 = em.find(Conta.class, 3L);
		if(conta1 == null) {
			System.out.print("Digite o saldo inicial: ");
			Double saldoConta1 = ler.nextDouble();
			conta1 = new Conta();
			conta1.setSaldo(saldoConta1);
		}
		
		Conta conta2 = em.find(Conta.class, 4L);
		if(conta2 == null) {
			System.out.print("Digite o saldo inicial: ");
			Double saldoConta2 = ler.nextDouble();
			conta2 = new Conta();
			conta2.setSaldo(saldoConta2);
		}
		
		em.getTransaction().begin();
		em.persist(conta1);
		em.persist(conta2);
		em.getTransaction().commit();
		
		System.out.println("Salto da conta 1: " + conta1.getSaldo() 
		+ " Saldo da canta 2: " + conta2.getSaldo());
		
		
		em.close();
		em = emf.createEntityManager();
		
		System.out.println("----------------------------");
		System.out.print("Digite o valor para a transferencia da conta1 para a conta2: ");
		Double valorTransferencia = ler.nextDouble();
		
		em.getTransaction().begin();
		conta1.setSaldo(conta1.getSaldo() - valorTransferencia);
		conta2.setSaldo(conta2.getSaldo() + valorTransferencia);
		
		em.merge(conta1);
		em.merge(conta2);
		
		if(conta1.getSaldo() >0) {
			em.getTransaction().commit();
			System.out.println("Transferencia feita com sucesso!");
		}else {
			em.getTransaction().rollback();
			System.err.print("Transferencia não realizada falda de saldo!");
		}
		
		
	}

}
