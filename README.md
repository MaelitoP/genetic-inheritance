[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<br />
<p align="center">
  <h3 align="center">Common Ancestors</h3>
  <p align="center"> Data Structure Course Project #2 </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#simulation">Simulation</a></li>
        <li><a href="#random life model">Random life model</a></li>
        <li><a href="#events">Events</a></li>
        <li><a href="#competences">Competences</a></li>
      </ul>
    </li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#references">References</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

In this course project we study genetic inheritance in a human population by simulation. In particular, we develop a platform to simulate random events (birth, coupling, death) in the lives of a population of sims, and we examine the coalescence of ancestral lineages.

### Simulation

The simulation follows a population of virtual individuals, or sims, which are objects with the following attributes:

* parents: mother and father
* date of birth
* date of death
* sex: male or female

The life of each sim advances by random events: birth, death and reproduction. The simulation runs in time by maintaining two data structures: the population of living sims, and the event queue.

### Random life model

Time is represented as a floating number on the scale 1.0=1 year, starting at 0.0 with the founder population.

The lifetime of a sim is a random number, following the Gompertz-Makeham law. This law combines a constant accident rate (of 1%/year by default), and a mortality rate increasing with age (doubling every 8 years by default).

Reproduction is described from the perspective of the mothers:
* mating is possible between two sims of different sex, and of ages bounded by minimum and maximum (by default, 16-73 for male, and 16-50 for female)
* the mother gives birth to children during her reproductive years, with the waiting time until mating following the exponential law with a fixed rate r (= Poisson process)
* when the time arrives, the mother chooses a partner at random, with preference for her previous partner (10% probability of changing partner by default)

### Events

We follow the evolution of the population by generating random events such as birth, death, or mating of sims.  Each event applies to a specific sim, and during the time of its occurrence, it can generate other events to follow.

After simulating a few thousand years, we can trace the ancestors of the current population of sims. If we go back in time in reverse, the ancestral lines merge and we will find less and less lines represented in the current population. We want to plot the number of ancestors in the current population as a function of time. For the fathers (forefathers), we maintain a set PA of paternal alleles by going back in time.

<p align="center">
  <a> <img src="output.png" alt="Output"> </a>
A POPULATION OF ~5000 INDIVIDUALS FOR 20000 YEARS (PRESENT TIME IS ON THE RIGHT). ANCESTRAL LINEAGES (FOREMOTHERS AND FOREFATHERS) MERGE RAPIDLY DURING THE RECENT ~300 YEARS   BUT INCREASINGLY RARELY IN THE MORE DISTANT PAST.
</p>

### Competences

* Priority queues implementation
* Symbol tables
* Random events: modeling & simulation

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<!-- CONTACT -->
## Contact

Maël LE PETIT - [@Maelito_P](https://twitter.com/Maelito_P) - contact@maelito.fr

Project Link: [https://github.com/MaelitoP/Lindenmayer](https://github.com/MaelitoP/Lindenmayer)



<!-- REFERENCES -->
## References
* [L-system](https://fr.wikipedia.org/wiki/L-Syst%C3%A8me)
* [P. Prusinkiewicz & A. Lindenmayer - The Algorithmic Beauty of Plants](http://algorithmicbotany.org/papers/abop/abop.pdf)
* [JSON](https://fr.wikipedia.org/wiki/JavaScript_Object_Notation)

<!-- MARKDOWN LINKS & IMAGES -->
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/MaelitoP/Lindenmayer/blob/main/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/maelitop/
[product-screenshot]: lindenmayer-tree.png
