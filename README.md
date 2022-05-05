# MagicParkour

# Configuration des parkour

La création d'un parkour se fait par son écriture dans le fichier de configuration `parkour.json`. Si il n'y a pas de fichier `parkour.json`, le plugin en créera un vide au démarrage.

***

## Types de valeurs

Tout au long de la configuration du parkour, vous aurez besoin de renseigner des valeurs telles que des vecteurs (coordonnées), des blocks, etc.

Voici les différents types de valeurs utilisées:

### Vecteurs

Les vecteurs sont des tableau de 3 nombres. Par exemple: `[2, 7, 8]` représente un vecteur qui a les valeurs x: 2, y: 7 et z: 8.

### Coordonnées

Les clés `"pos"` attendent toujours une coordonnée.

Les coordonnées sont des vecteurs qui représentent une position absolue dans le monde. Par exemple: `"pos": [237, 86, 76]` représente les coordonnées d'un bloc positionné en x: 237, y: 86 et z: 76

### BlockData

Les clés `"block"` attendent toujours un BlockData. Cependant d'autres clés peuvent aussi attendre ce genre de valeurs.

Cette valeur se présente sous la forme d'un string représentant un block. Par exemple: `"stone"`, `"minecraft:white_stained_glass"`, ou encore `"ladder[facing=east]"` sont des BlockData valides.

Vous l'avez peut-être remarqué, c'est la même forme qui est utilisée en vanilla avec les commande `/setblock` ou `/fill`

Malheureusement le plugin ne supporte pas d'associer des NBT aux tile entities. Cela signifie qu'on ne peut pas renseigner un panneau avec du texte ou un coffre contenant des items, comme on peut le faire en vanilla avec par exemple `oak_sign{Text1:'{"text":"Line1"}'}`

### Types primitifs

Vous aurez aussi parfois besoin de renseigner des types primitifs json, comme des strings ou des booléens.

***

## Parkour.json

Le fichier `parkour.json` est composé d'une première section contenant les différents parkours. Chaque clé est l'id d'un parkour, la sous-section qui lui est associée contient toutes les données du parkour.

Voici par exemple un fichier qui contient 3 parkours ayant pour id respectivement `lobby`, `parkour2` et `parkour3`. Pour plus de lisibilité, les données des parkours ont été omises.

```json
{
  "lobby": {},
  "parkour2": {},
  "parkour3": {}
}
```

***

## Définir un parkour

Pour être valide, un parkour doit nécéssairement contenir certains attributs, mais certains sont optionnels pour plus de personnalisation.

Voici un exemple simple de parkour:
```json
{
  "lobby": {
    "world": "world",
    "base_block": "cyan_stained_glass",
    "checkpoint_block": "diamond_block",
    "start": {
      "pos": [231, 85, 79]
    },
    "steps": [
      {
        "pos": [231, 86, 82]
      },
      {
        "type": "obstacle",
        "pos": [230, 88, 85],
        "blocks": [
          { "offset": [0, 0, 0] }
        ]
      },
      {
        "pos": [230, 86, 86]
      }
    ],
    "end": {
      "pos": [228, 87, 84]
    }
  }
}
```

## Éléments requis

Voici une liste des éléménts nécéssaires pour qu'un parkour soit valide.
Si un seul de ces éléments manque ou n'est pas valide, le parkour ne se chargera pas et une erreur sera envoyée dans la console.

### "world"

`"world"` correspond à l'id du monde dans lequel le parkour se trouve.

### "start"

Cette section contient les données du point de départ du parkour.

Il est nécéssaire d'y renseigner l'attribut `"pos"` qui précisera les [coordonnées](#coordonnées) du point de départ.

On peut aussi y renseigner l'attribut `"block"` pour définir le [block](#blockdata) qui sera utilisé comme point de départ. La valeur par défaut est `"gold_block"`

Voici un exemple de section `"start"`:

```json
"start": {
  "pos": [235, 85, 74],
  "block": "gold_ore"
}
```

### "end"

Cette section est similaire à la section `"start"`, sauf qu'elle définit **le dernier block** du parkour. La valeur par défaut de `"block"` pour cette section est `"diamond_block"`

### "steps"

`"steps"` a pour valeur un tableau d'éléments représentant chacun un *step* (étape) du parkour. Les steps les plus simples prennent souvent la forme de simples blocs.

- Ce tableau est souvent la partie la plus longue du fichier, car elle définit chaque step un par un.

- Pour terminer le parkour, le joueur doit parcourir tous les steps dans l'ordre puis arriver sur le block défini dans `"end"`.

Il existe plusieurs types de [steps](#step) ayant chacun des propriétés et des effets différents.

## Éléments optionnels

### "customName"

Cet attribut est un string qui définit le nom qui sera affiché lorsqu'un joueur commence le parkour. Il peut contenir des codes couleurs ou de style tels que `§e`, `§l`. Si aucun code couleur n'est spécifié, le nom s'affichera en orange (code couleur `§6`). Voici une [liste des codes couleurs](https://minecraft.tools/fr/color-code.php) que j'utilise souvent quand j'ai un trou de mémoire.

Par exemple
```json
"customName": "§a§lSky line"
```
fera apparaître le message "Début du parkour **Sky line**" (avec "Sky line" en vert et en gras) quand un joueur commencera le parkour.

Si cet attribut n'est pas spécifié, l'id du parkour sera utilisé comme nom.

### "base_block"

Cet attribut définis le [block](#blockdata) qui sera utilisé par défaut pour tous les steps de type [simple](#simple) qui n'ont pas de valeur `"block"` définie.

La valeur par défaut de cet attribut est `"white_stained_glass"`.

### "checkpoint_block"

Cet attribut fonctionne de la même facon que ["base_block"](#base_block), mais définit le block qui sera utilisé pour les steps de type [checkpoint](#checkpoint).

Sa valeur par défaut est `"gold_block"`.

### "obstacle_block"

Vous l'aurez deviné, cet attribut définit le [block](#blockdata) par défaut qui sera utilisé pour tous les blocks des steps de type [obstacle](#obstacle) qui n'ont pas de valeur `"block"` définie.

Sa valeur par défaut est `"light_gray_stained_glass"`.

***

## Step

Il existe plusieurs types de steps permettant de créer des parkour moins monotones.
Chaque type de step différent prendra des valeurs différentes des autres types, mais certains attributs sont communs à tous ou reviennent souvent.

Voici les attributs qui sont communs à tous les steps:

### "type"

L'attribut `"type"` définit de quel type le step est.

Il peut prendre les valeurs suivantes:
- `"simple"`
- `"checkpoint"`
- `"slime"`
- `"obstacle"`

Il n'est pas obligatoire et s'il n'est pas défini, il prendra la valeur `"simple"`.

### "pos"

L'attribut `"pos"` définit la position, ou l'origine du step. Il a pour valeur une [coordonnée](#coordonnées).

Il est obligatoire et s'il n'est pas défini, le parkour ne chargera pas.

## Types de steps

Dans cette section nous allons voir tous les types de steps et leur propriétés.

### simple

Le step `"simple"` et le plus simple des steps: il n'est composé que d'un block.

Il prend 3 attributs (en + de `"type"`):
- `"pos"` définit les [coordonnées](#coordonnées) du bloc.
- (*optionnel*) `"block"` définit le [block](#blockdata) qui compose ce step.
- (*optionnel*) `"scope"` définit la valeur [scope](#scope) du step. Valeur par défaut: `true`.

Voici deux exemples de steps `"simple"` valides:

```json
{
  "pos": [237, 86, 76]
}
```

```json
{
  "type": "simple",
  "pos": [237, 86, 76],
  "block": "stone",
  "scope": false
}
```

### checkpoint

Le step `"checkpoint"` est similaire au step `"simple"` avec une différence majeure: Une fois que je joueur y est parvenu, il y est re-téléporté dès qu'il tombe. Le joueur peut aussi y revenir grâce à la commande `/parkour checkpoint`.

Il prend les 3 mêmes attributs que le step `"simple"`. Son scope par défaut est `true`.

### slime

Le step `"slime"` est un step qui forme une plateforme de blocs de slime d'une taille définie.

Il prend 4 valeurs:
- `"pos"` définit les [coordonnées](#coordonnées) du coin ayant les plus petites coordonnées x et z (le coin nord-ouest).
- (*optionnels*) `"sizeX"` et `sizeZ` sont des entiers qui définissent la taille de la plateforme sur les axes X et Z. Leur valeur par défaut est `1`.
- (*optionnel*) `"scope"` définit la valeur [scope](#scope) du step. Valeur par défaut: `false`.

Voici l'exemple d'un step `"slime"` qui forme une plateforme de 2 par 3 blocs de slime:

```json
{
  "type": "slime",
  "pos": [244, 80, 99],
  "sizeX": 3,
  "sizeZ": 2
}
```

### obstacle

Le step `"obstacle"` est un step qui ne nécessite pas d'être parcouru, mais permet d'ajouter des blocs au parkour de facon à aider/déranger le joueur. Il peut être composé de plusieurs blocs.

Il prend 2 valeurs:
- `"pos"` définit les coordonnées à l'*origine* du step obstacle.
- `"blocks"` est un tableau d'éléments définissant les différents blocs qui composent l'osbtacle.

#### Bloc d'obstacle

Chaque bloc de l'obstacle est défini par une section json qui prend 2 arguments:
- `"offset"` est un vecteur qui, additionné à l'*origine* du step obstacle, donne la coordonnée du bloc.
- (*optionnel*) `"block"` définis le [block](#blockdata) représenté par cet élément. Sa valeur par défaut est la valeur ["obstacle_block"](#obstacle_block) définie plus tôt dans le parkour.

#### Exemple

Voici un exemple de step obstacle composé de 2 blocs empilés sur lequels sont positionnés des échelles. Les 2 blocs seront ceux définis par ["obstacle_block"](#obstacle_block).

```json
{
  "type": "obstacle",
  "pos": [230, 88, 85],
  "blocks": [
    { "offset": [0, 0, 0] },
    { "offset": [1, 0, 0], "block": "ladder[facing=east]" },
    { "offset": [0, 1, 0] },
    { "offset": [1, 1, 0], "block": "ladder[facing=east]" }
  ]
}
```

***

## Scope

*Si vous ne comprenez rien à cette section, de 1 c'est parce que j'explique mal, et de 2 ce n'est pas grave. Comprendre le mécanisme scope peut etre utile dans des cas assez spécifiques, mais en général vous n'en aurez pas besoin pour créer un parkour.*

Le scope est le nombre de [steps](#step) qui apparaissent devant et derrière le joueur. Sa valeur est de `2`. Le plugin ne permet pas encore de modifier cette valeur dans le fichier de configuration.

Certains steps peuvent ne pas prendre de scope, et alors le nombre de steps qui apparaissent sera augmenté.

On peut imaginer le scope comme un *nombre* qui part du step où le joueur se trouve avec une valeur de 2, et qui avance de step en step jusqu'a ce qu'il atteigne 0. Chaque fois que le *nombre* traverse un step a qui la valeur `"scope"` est `true`, il perd 1. Le step sur lequel le joueur se trouve ne compte pas.

En d'autres mots, il sera affiché devant le joueur un total de 2 steps qui ont `"scope"` à `true` plus tous les steps avant.

Il est possible de modifier la valeur `"scope"` de la plupart des steps.

***

# Configuration des messages

Il existe un fichier `messages.yml` permettant de modifier a votre guise tous les messages utilisés par le plugin.

S'il n'existe pas, il est généré au démarrage du plugin.

Par défaut, il est généré avec tous les messages du plugin. Vous pouvez les modifier pour personnaliser l'experience utilisateur! Cependant, attention a ne pas mettre n'importe quoi, sinon le plugin sera inutilisable..

S'il manque un message, le message par défaut sera utilisé.
