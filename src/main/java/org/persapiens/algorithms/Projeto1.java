/*
 * Copyright 2016-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.persapiens.algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.persapiens.algorithms.tree.RedBlackTreeNodeColor;
import org.persapiens.algorithms.tree.StringRedBlackTree;
import org.persapiens.algorithms.tree.StringRedBlackTreeNode;

/**
 * Main class.
 * @author Marcelo Fernandes
 */
public class Projeto1 {

	private StringRedBlackTree tree;
	
	public Projeto1() {
		tree = new StringRedBlackTree();
	}

	private void comandoIncluir(String key) {
		StringRedBlackTreeNode node = tree.search(key);
		if (node.equals(tree.getNill())) {
			tree.insert(key);
		}
		else {
			System.out.println("");
			System.out.println("A palavra " + key + " ja foi inserida.");
		}		
	}
	
	private void comandoRemover(String key) {
		StringRedBlackTreeNode node = tree.search(key);
		if (!node.equals(tree.getNill())) {
			System.out.println("");
			System.out.println("Removendo a palavra " + key);
			tree.delete(key);
			print();
			check();
		}
		else {
			System.out.println("");
			System.out.println("A palavra " + key + " foi removida anteriormente ou nao foi inserida.");
		}
		
	}
	
	/**
	 * Imprime a lista ordenada de todas as chaves armazenadas na árvore T.
	 */
	private void print() {
		String result = "";
		for(String s : tree.sort()) {
			result += s + ", ";
		}
		System.out.println(result);
	}
	
	/**
	 * - Imprime, para cada nó da árvore, as seguintes informações: chave do nó 
	 * pai, chave própria, cor do nó, altura negra, chaves dos filhos. A árvore 
	 * deve ser visitada em pré-ordem. Você deve imprimir um nó por linha, como 
	 * pode ser visto no exemplo abaixo: 
	 * (NIL, fortuito, preto, 3, esperança, malária)
	 * (fortuito, esperança, vermelho, 3, doido, facão)
	 */
	private void check() {
		List<StringRedBlackTreeNode> preorder = tree.preorder();
		for(int counter = 0; counter < preorder.size(); counter++) {
			check(preorder.get(counter));
			if (counter != preorder.size() -1)
			{
				System.out.println("");
			}
		}
	}

	private String keyToString(StringRedBlackTreeNode node) {
		String result = "Nil";
		if(node.getKey() != null) {
			result = node.getKey();
		}
		return result;
	}
	
	private void check(StringRedBlackTreeNode node) {
		String result = "(";
		
		result += keyToString(node.getParent()) + ", ";
		result += node.getKey() + ", ";
		result += node.getColor().equals(RedBlackTreeNodeColor.BLACK) ? "preto" : "vermelho";
		result += ", ";
		result += tree.getBlackHeight(node) + ", ";
		result += keyToString(node.getLeft()) + ", ";
		result += keyToString(node.getRight());
		
		result += ")";
		System.out.println(result);
	}
	
	public void processarDicionario(File dicionario) throws IOException {
		try (BufferedReader bis = new BufferedReader(new FileReader(dicionario))) {
			String line;
			while((line = bis.readLine()) != null) {
				line = line.trim();
				String key = line.substring(0, line.lastIndexOf(' '));
				String comando = line.substring(line.lastIndexOf(' ') + 1);
				if (comando.equals("1")) {
					comandoIncluir(key);
				}
				else if (comando.equals("0")) {
					comandoRemover(key);
				}
			}
			System.out.println("");
			print();
			check();
		}
	}

}
